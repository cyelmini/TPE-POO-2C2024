package frontend;

import backend.CanvasState;
import backend.model.*;
import frontend.Draw.Buttons.*;
import frontend.Draw.DrawFigure;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;


import java.util.*;

public class PaintPane extends BorderPane {

    /* --------------------------------- Canvas y relacionados ----------------------------------- */
	private final Canvas canvas = new Canvas(800, 600);
	private final GraphicsContext gc = canvas.getGraphicsContext2D();
	private final Color lineColor = Color.BLACK;
	private final Color defaultFillColor = Color.YELLOW;
	private final Color defaultGradientColor = defaultFillColor;
	private final double duplicateOffset = 10.0;

	/* ------------------------------------- Botones -------------------------------------------- */

	/* Botones barra izquierda */
	private final ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private final ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private final ToggleButton circleButton = new ToggleButton("Círculo");
	private final ToggleButton squareButton = new ToggleButton("Cuadrado");
	private final ToggleButton ellipseButton = new ToggleButton("Elipse");

    /* Botones para las sombras */
	private final ChoiceBox<ShadowType> shadowTypeChoiceBox = new ChoiceBox<>();

	/* Boton checkbox biselado */
	private final CheckBox beveledCheckBox = new CheckBox("Biselado");

	/* Boton selector de color de relleno */
	private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	/* Boton selector de color de gradiente */
	private final ColorPicker gradientColorPicker = new ColorPicker(defaultGradientColor);

	/* Boton copiar formato */
	private final ToggleButton copyFormatButton = new ToggleButton("Copiar fmt");

    private final ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();

    /* ------------------------------------ Dibujo de figuras -------------------------------------- */

	/* Dibujar una figura */
	private Point startPoint;

	/* Seleccionar una figura */
	private DrawFigure selectedFigure;

    /* Mapas de Layers-Drawfigures */
	private final Map<Layer, List<DrawFigure>> layersMap = new LinkedHashMap<>();
	
	public PaintPane(CanvasState<DrawFigure> canvasState, StatusPane statusPane) {

        /* ----------------------------------- Inicializar botones -----------------------------------*/

		/* Inicializar botones de la izquierda */
        ToggleButton deleteButton = new ToggleButton("Borrar");
        ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton,
                deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setToggleGroup(tools);
			initializeButtons(tool);
		}
		setFigureButtons();

		/* Inicializar shadowbox */
		shadowTypeChoiceBox.getItems().addAll(ShadowType.values());
		shadowTypeChoiceBox.setValue(ShadowType.NO_SHADOW);
		shadowTypeChoiceBox.setPrefWidth(90);
		shadowTypeChoiceBox.setCursor(Cursor.HAND);

		/* Inicializar biselado */
		beveledCheckBox.setSelected(false);
		beveledCheckBox.setPrefWidth(90);
		beveledCheckBox.setCursor(Cursor.HAND);

		/* Color pickers */
		fillColorPicker.setCursor(Cursor.HAND);
		gradientColorPicker.setCursor(Cursor.HAND);

		/* Inicializa botón para copiar el formato */
		initializeButtons(copyFormatButton);

		/* Inicializa botones de la barra derecha, barra superior */
        Button turnRightButton = new Button("Girar D");
        Button turnHorizontalButton = new Button("Voltear H");
        Button turnVerticalButton = new Button("Voltear V");
        Button duplicateButton = new Button("Duplicar");
        Button divideButton = new Button("Dividir");

        Button frontButton = new Button("Traer al frente");
        Button backButton = new Button("Enviar al fondo");
        Button addLayerButton = new Button("Agregar capa");
        Button removeLayerButton = new Button("Eliminar capa");

		Button[] buttons = {turnRightButton, turnHorizontalButton, turnVerticalButton,
                duplicateButton, divideButton, frontButton, backButton, addLayerButton, removeLayerButton};
		for(Button button : buttons){
			initializeButtons(button);
		}

		/* Inicializar choiceBox capas */
		for(int i = 1 ; i < 4 ; i++) {
			layersMap.put(new Layer("Capa " + i, i), new LinkedList<>());
		}
		layerChoiceBox.setValue(layersMap.keySet().iterator().next());
		layerChoiceBox.getItems().addAll(layersMap.keySet());
		layerChoiceBox.setMinWidth(90);
		layerChoiceBox.setCursor(Cursor.HAND);

		/* Inicializar botones mostrar y ocultar capas */
        RadioButton showLayerButton = new RadioButton("Mostrar");
		showLayerButton.setSelected(true);
		RadioButton hideLayerButton = new RadioButton("Ocultar");
		RadioButton[] showHideArr = {showLayerButton, hideLayerButton};
		ToggleGroup showHide = new ToggleGroup();
		for(RadioButton radioButton : showHideArr){
			radioButton.setToggleGroup(showHide);
			radioButton.setCursor(Cursor.HAND);
		}

		gc.setLineWidth(1);

		/* Se crea un VBox para los botones de la barra izquierda */
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(new Label("Formato"));
		buttonsBox.getChildren().addAll(shadowTypeChoiceBox, beveledCheckBox, fillColorPicker,
				gradientColorPicker, copyFormatButton);

		/* Formato de la VBox izquierda */
		initializeBox(buttonsBox);

		/* Se crea un Vbox para los botones de la barra derecha */
		VBox leftButtonsBox = new VBox(10);
		leftButtonsBox.getChildren().add(new Label("Acciones:"));
		leftButtonsBox.getChildren().addAll(turnRightButton, turnHorizontalButton, turnVerticalButton,
                duplicateButton, divideButton);

		/* Formato de la VBox derecha */
		initializeBox(leftButtonsBox);

		/* Se crea un HBox para los botones de la barra superior */
		HBox topButtonsBox = new HBox(10);
		topButtonsBox.getChildren().addAll(frontButton, backButton);
		topButtonsBox.getChildren().add(new Label("Capas"));
		topButtonsBox.getChildren().addAll(layerChoiceBox);
        topButtonsBox.getChildren().addAll(showLayerButton, hideLayerButton, addLayerButton, removeLayerButton);

		/* Formato de la HBox superior */
		setTop(topButtonsBox);
		topButtonsBox.setAlignment(Pos.CENTER_LEFT);
		initializeBox(topButtonsBox);

		Region leftSpacer = new Region();
		Region rightSpacer = new Region();
		HBox.setHgrow(leftSpacer, Priority.ALWAYS);
		HBox.setHgrow(rightSpacer, Priority.ALWAYS);
		topButtonsBox.getChildren().addFirst(leftSpacer);
		topButtonsBox.getChildren().add(rightSpacer);

		/* --------------------------------------- Manejo de acciones ------------------------------------------- */
		canvas.setOnMousePressed(event -> startPoint = new Point(event.getX(), event.getY()));

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());

			if(drawFigureButtonsSelected() && (startPoint == null || (endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()))) {
				setErrorAlert("Error en el dibujo de figura", "Debe dibujar la figura arrastrando el mouse hacia " +
						"abajo y hacia la derecha");
				return;
			}

			DrawFigure newFigure;
			Toggle selectedButton = tools.getSelectedToggle();

			if(selectedButton == null || selectedButton.getUserData() == null) {
				return;
			}

			if(!layerChoiceBox.getValue().isVisible()){
				setErrorAlert("La capa seleccionada está oculta", "Seleccione otra capa");
				return;
			}

			Buttons button = (Buttons)selectedButton.getUserData();
			newFigure = button.getDrawFigure(startPoint, endPoint, fillColorPicker.getValue(),
					gradientColorPicker.getValue(), gc, shadowTypeChoiceBox.getValue(), beveledCheckBox.isSelected(),
					layerChoiceBox.getValue());
			canvasState.add(newFigure);
			layersMap.putIfAbsent(layerChoiceBox.getValue(), new LinkedList<>());
			layersMap.get(layerChoiceBox.getValue()).add(newFigure);
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(DrawFigure drawFigure : canvasState.reversed()) {
				if(drawFigure.found(eventPoint) && drawFigure.getLayer().isVisible()) {
					found = true;
					label.append(drawFigure);
					break;
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for(Layer layer : layersMap.keySet()){
					for(DrawFigure drawFigure : layersMap.get(layer).reversed()) {
						if (drawFigure.found(eventPoint) && drawFigure.getLayer().isVisible()) {
							if (selectedFigure != null && copyFormatButton.isSelected()) {
								drawFigure.format(selectedFigure);
								copyFormatButton.setSelected(false);
							} else {
								selectedFigure = drawFigure;
								fillColorPicker.setValue(drawFigure.getFillColor());
								gradientColorPicker.setValue(drawFigure.getGradientColor());
								shadowTypeChoiceBox.setValue(drawFigure.getShadowType());
								beveledCheckBox.setSelected(drawFigure.isBeveled());
								layerChoiceBox.setValue(drawFigure.getLayer());
								label.append(drawFigure);
								found = true;
								break;
							}
						}
					}
				}
				if(found) {
					statusPane.updateStatus(label.toString());
				} else {
					selectedFigure = null;
					statusPane.updateStatus("Ninguna figura encontrada");
				}
				redrawCanvas();
			}
		});

		canvas.setOnMouseDragged(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				double diffX = (eventPoint.getX() - startPoint.getX()) / 100;
				double diffY = (eventPoint.getY() - startPoint.getY()) / 100;

				if(selectedFigure != null && canvasState.contains(selectedFigure)) {
					selectedFigure.move(diffX, diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if(selectedFigure != null && canvasState.contains(selectedFigure)) {
				canvasState.remove(selectedFigure);
				layersMap.get(selectedFigure.getLayer()).remove(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			if(selectionButton.isSelected() && selectedFigure != null){
				selectedFigure.setPrimaryColor(fillColorPicker.getValue());
				redrawCanvas();
			}
		});

		gradientColorPicker.setOnAction(event -> {
			if(selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setSecondaryColor(gradientColorPicker.getValue());
				redrawCanvas();
			}
		});

		shadowTypeChoiceBox.setOnAction(event -> {
			if(selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setShadowType(shadowTypeChoiceBox.getValue());
				redrawCanvas();
			}
		});

		beveledCheckBox.setOnAction(event -> {
			if( selectionButton.isSelected() && selectedFigure != null) {
				selectedFigure.setBeveled(beveledCheckBox.isSelected());
				redrawCanvas();
			}
		});

		copyFormatButton.setOnAction(event -> {
			if(selectedFigure == null){
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para copiar el formato");
			}
		});

		turnRightButton.setOnMouseClicked(event -> {
			if(selectedFigure != null) {
				selectedFigure.turnRight();
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para girarla a la derecha");
			}
		});

		turnHorizontalButton.setOnMouseClicked(event ->{
			if(selectedFigure != null) {
				selectedFigure.turnHorizontal();
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para moverla horizontalmente");
			}
		});

		turnVerticalButton.setOnMouseClicked(event -> {
			if(selectedFigure != null) {
				selectedFigure.turnVertical();
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para moverla verticalmente");
			}
		});

		duplicateButton.setOnAction(event -> {
			if(selectedFigure != null) {
				DrawFigure newFigure = selectedFigure.duplicate(duplicateOffset);
				canvasState.add(newFigure);
				layersMap.get(newFigure.getLayer()).add(newFigure);
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para duplicarla");
			}
		});

		divideButton.setOnAction(event -> {
			if(selectedFigure != null) {
				DrawFigure newFigure = selectedFigure.divide();
				canvasState.add(newFigure);
				layersMap.get(newFigure.getLayer()).add(newFigure);
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para dividirla");
			}
		});

		showLayerButton.setOnAction(event -> {
			layerChoiceBox.getValue().setVisible(true);
			redrawCanvas();
		});

		hideLayerButton.setOnAction(event -> {
			layerChoiceBox.getValue().setVisible(false);
			if(selectedFigure != null && selectedFigure.getLayer().getNumber() == layerChoiceBox.getValue().getNumber()){
				selectedFigure = null;
			}
			redrawCanvas();
		});

		frontButton.setOnAction(event -> {
			if(selectedFigure != null) {
				layersMap.get(layerChoiceBox.getValue()).remove(selectedFigure);
				layersMap.get(layerChoiceBox.getValue()).add(selectedFigure);
				canvasState.remove(selectedFigure);
				canvasState.add(selectedFigure);
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para traerla al frente");
			}
		});

		backButton.setOnAction(event -> {
			if(selectedFigure != null) {
				layersMap.get(layerChoiceBox.getValue()).remove(selectedFigure);
				layersMap.get(layerChoiceBox.getValue()).addFirst(selectedFigure);
				canvasState.remove(selectedFigure);
				canvasState.addFirst(selectedFigure);
				redrawCanvas();
			} else {
				setErrorAlert("Figura no seleccionada", "Debe seleccionar una figura para enviarla al fondo");
			}
		});

		layerChoiceBox.setOnAction(event -> {
			Layer selectedLayer = layerChoiceBox.getValue();
			showLayerButton.setSelected(true);
			selectedLayer.setVisible(true);
			redrawCanvas();
		});

		addLayerButton.setOnAction(event ->{
			Layer lastLayer = layerChoiceBox.getItems().getLast();
			Layer newLayer = new Layer("Capa " + (lastLayer.getNumber() + 1), lastLayer.getNumber() + 1);
			layersMap.put(newLayer, new LinkedList<>());
			layerChoiceBox.getItems().add(newLayer);
			layerChoiceBox.setValue(newLayer);
			redrawCanvas();
		});

		removeLayerButton.setOnAction(event -> {
			if(layerChoiceBox.getValue().getNumber() > 3){
				layersMap.remove(layerChoiceBox.getValue());
				layerChoiceBox.getItems().remove(layerChoiceBox.getValue());
				layerChoiceBox.setValue(layerChoiceBox.getItems().getFirst());
				redrawCanvas();
			} else {
				setErrorAlert("No se pueden borrar las primeras tres capas", "Intente nuevamente");
			}
		});

		setLeft(buttonsBox);
		setRight(leftButtonsBox);
		setCenter(canvas);
	}

	/* ----------------------------------------- Funciones auxiliares -----------------------------------------*/

	/* Redibujar el canvas */
	private void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for(Layer layer : layersMap.keySet()){
			if(layer.isVisible()){
				for(DrawFigure drawFigure : layersMap.get(layer)){
					if(selectedFigure == null){
						drawFigure.draw(null, lineColor);
					} else {
						drawFigure.draw(selectedFigure.getFigure(), lineColor);
					}
				}
			}
		}
	}

	/* Inicializar los valores de los botones para cada figura */
	private void setFigureButtons(){
		circleButton.setUserData(new CircleButton());
		ellipseButton.setUserData(new EllipseButton());
		rectangleButton.setUserData(new RectangleButton());
		squareButton.setUserData(new SquareButton());
	}

	/* Inicializar botones */
	private void initializeButtons(ButtonBase button){
		button.setMinWidth(90);
		button.setCursor(Cursor.HAND);
	}

	/* Inicializar box */
	private void initializeBox(Pane box){
		box.setPadding(new Insets(5));
		box.setStyle("-fx-background-color: #999");
		box.setPrefWidth(100);
		gc.setLineWidth(1);
	}

	/* Mensaje de alerta */
	private void setErrorAlert(String header, String content){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	/* Verificar si se seleccionó algún botón de figura */
	private boolean drawFigureButtonsSelected() {
		return rectangleButton.isSelected() || circleButton.isSelected() || squareButton.isSelected() || ellipseButton.isSelected();
	}
}
