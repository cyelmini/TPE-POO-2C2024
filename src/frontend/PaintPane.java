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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;


import java.util.*;

public class PaintPane extends BorderPane {

	/* ------------------------------------- BackEnd --------------------------------------------- */
	private final CanvasState canvasState;

	/* --------------------------------- Canvas y relacionados ---------------------------------- */
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
	private final ToggleButton deleteButton = new ToggleButton("Borrar");

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

	/* Botones barra derecha */
	private final ToggleButton turnRightButton = new ToggleButton("Girar D");
	private final ToggleButton turnHorizontalButton = new ToggleButton("Voltear H");
	private final ToggleButton turnVerticalButton = new ToggleButton("Voltear V");
	private final ToggleButton duplicateButton = new ToggleButton("Duplicar");
	private final ToggleButton divideButton = new ToggleButton("Dividir");

	/* Botones barra superior */
	private final ToggleButton frontButton = new ToggleButton("Traer al frente");
	private final ToggleButton backButton = new ToggleButton("Enviar al fondo");
	private final ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();
	private final RadioButton showLayerButton = new RadioButton("Mostrar");
	private final RadioButton hideLayerButton = new RadioButton("Ocultar");
	private final ToggleButton addLayerButton = new ToggleButton("Agregar capa");
	private final ToggleButton removeLayerButton = new ToggleButton("Eliminar capa");

	/* ------------------------------------ Dibujo de figuras -------------------------------------- */

	/* Dibujar una figura */
	private Point startPoint;

	/* Seleccionar una figura */
	private DrawFigure selectedFigure;

	/* StatusBar */
	private final StatusPane statusPane;

	/* Lista de DrawFigures */
	private final List<DrawFigure> drawFigures = new LinkedList<>();

	/* Mapas de Layers-Drawfigures */
	private final Map<Layer, List<DrawFigure>> layersMap = new LinkedHashMap<>();
	
	public PaintPane(CanvasState canvasState, StatusPane statusPane) {

		this.canvasState = canvasState;
		this.statusPane = statusPane;

		/* ------------------------------------- Inicializar botones -----------------------------------*/

		/* Inicializar botones de la izquierda */
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton,
				deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		setFigureButtons();

		/* Inicializar shadowbox */
		shadowTypeChoiceBox.getItems().addAll(ShadowType.values());
		shadowTypeChoiceBox.setValue(ShadowType.NO_SHADOW); // Valor predeterminado
		shadowTypeChoiceBox.setPrefWidth(90);
		shadowTypeChoiceBox.setCursor(Cursor.HAND);

		/* Inicializar biselado */
		beveledCheckBox.setPrefWidth(90);
		beveledCheckBox.setSelected(false);
		beveledCheckBox.setCursor(Cursor.HAND);

		/* Color pickers */
		fillColorPicker.setCursor(Cursor.HAND);
		gradientColorPicker.setCursor(Cursor.HAND);

		/* Inicializa botón para copiar el formato */
		copyFormatButton.setMinWidth(90);
		copyFormatButton.setCursor(Cursor.HAND);

		/* Inicializa botones de la barra derecha */
		ToggleButton[] actionsArr = {turnRightButton, turnHorizontalButton, turnVerticalButton, duplicateButton, divideButton};
		ToggleGroup actions = new ToggleGroup();
		for(ToggleButton action : actionsArr){
			action.setMinWidth(90);
			action.setToggleGroup(actions);
			action.setCursor(Cursor.HAND);
		}

		/* Inicializa botones de la barra superior */
		ToggleButton[] layersArr = {frontButton, backButton, addLayerButton, removeLayerButton};
		ToggleGroup layers = new ToggleGroup();
		for(ToggleButton layerButton : layersArr){
			layerButton.setMinWidth(90);
			layerButton.setToggleGroup(layers);
			layerButton.setCursor(Cursor.HAND);
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
		ToggleGroup showHide = new ToggleGroup();
		showLayerButton.setToggleGroup(showHide);
		showLayerButton.setCursor(Cursor.HAND);
		hideLayerButton.setToggleGroup(showHide);
		showLayerButton.setCursor(Cursor.HAND);
		showHide.selectToggle(showLayerButton);

		/* Se crea un VBox para los botones de la barra izquierda */
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(new Label("Formato"));
		buttonsBox.getChildren().addAll(shadowTypeChoiceBox, beveledCheckBox, fillColorPicker,
				gradientColorPicker, copyFormatButton);

		/* Formato de la VBox izquierda */
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		/* Se crea un Vbox para los botones de la barra derecha */
		VBox leftButtonsBox = new VBox(10);
		leftButtonsBox.getChildren().add(new Label("Acciones:"));
		leftButtonsBox.getChildren().addAll(turnRightButton, turnHorizontalButton, turnVerticalButton,
				duplicateButton, divideButton);

		/* Formato de la VBox derecha */
		leftButtonsBox.setPadding(new Insets(5));
		leftButtonsBox.setStyle("-fx-background-color: #999");
		leftButtonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		/* Se crea un HBox para los botones de la barra superior */
		HBox topButtonsBox = new HBox(10);
		topButtonsBox.getChildren().addAll(frontButton, backButton);
		topButtonsBox.getChildren().add(new Label("Capas"));
		topButtonsBox.getChildren().addAll(layerChoiceBox);
		topButtonsBox.getChildren().addAll(showLayerButton, hideLayerButton, addLayerButton, removeLayerButton);

		/* Formato de la HBox superior */
		setTop(topButtonsBox);
		topButtonsBox.setAlignment(Pos.CENTER_LEFT);
		topButtonsBox.setPadding(new Insets(5));
		topButtonsBox.setStyle("-fx-background-color: #999");
		topButtonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		Region leftSpacer = new Region();
		Region rightSpacer = new Region();
		HBox.setHgrow(leftSpacer, Priority.ALWAYS);
		HBox.setHgrow(rightSpacer, Priority.ALWAYS);
		topButtonsBox.getChildren().addFirst(leftSpacer);
		topButtonsBox.getChildren().add(rightSpacer);

		/* --------------------------------------- Manejo de acciones ------------------------------------------- */

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());

			if(startPoint == null) {
				return ;
			}

			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
				return ;
			}

			DrawFigure newFigure = null;
			Toggle selectedButton = tools.getSelectedToggle();

			if(selectedButton == null || selectedButton.getUserData() == null){
				return;
			}

			Buttons button = (Buttons)selectedButton.getUserData();
			newFigure = button.getDrawFigure(startPoint, endPoint, fillColorPicker.getValue(),
					gradientColorPicker.getValue(), gc, shadowTypeChoiceBox.getValue(), beveledCheckBox.isSelected(),
					layerChoiceBox.getValue());
			drawFigures.add(newFigure);
			layersMap.putIfAbsent(layerChoiceBox.getValue(), new LinkedList<>());
			layersMap.get(layerChoiceBox.getValue()).add(newFigure);
			canvasState.add(newFigure.getFigure());
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(DrawFigure drawFigure : drawFigures) {
				if(drawFigure.found(eventPoint)) {
					found = true;
					label.append(drawFigure);
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
				for(DrawFigure drawFigure : drawFigures) {
					if(drawFigure.found(eventPoint)) {
						if(selectedFigure != null && copyFormatButton.isSelected()){
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

				if(selectedFigure != null && canvasState.contains(selectedFigure.getFigure())) {
					selectedFigure.move(diffX, diffY);
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if(selectedFigure != null && canvasState.contains(selectedFigure.getFigure())) {
				drawFigures.remove(selectedFigure);
				canvasState.remove(selectedFigure.getFigure());
				layersMap.get(selectedFigure.getLayer()).remove(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			if(selectedFigure != null){
				selectedFigure.setPrimaryColor(fillColorPicker.getValue());
				redrawCanvas();
			}
		});

		gradientColorPicker.setOnAction(event -> {
			if(selectedFigure != null) {
				selectedFigure.setSecondaryColor(gradientColorPicker.getValue());
				redrawCanvas();
			}
		});

		shadowTypeChoiceBox.setOnAction(event -> {
			if(selectedFigure != null) {
				selectedFigure.setShadowType(shadowTypeChoiceBox.getValue());
				redrawCanvas();
			}
		});

		beveledCheckBox.setOnAction(event -> {
			if(selectedFigure != null) {
				selectedFigure.setBeveled(beveledCheckBox.isSelected());
				redrawCanvas();
			}
		});

		turnRightButton.setOnMouseClicked(event -> {
			if(selectedFigure != null) {
				selectedFigure.turnRight();
				redrawCanvas();
			}
		});

		turnHorizontalButton.setOnMouseClicked(event ->{
			if(selectedFigure != null) {
				selectedFigure.turnHorizontal();
				redrawCanvas();
			}
		});

		turnVerticalButton.setOnMouseClicked(event -> {
			if(selectedFigure != null) {
				selectedFigure.turnVertical();
				redrawCanvas();
			}
		});

		duplicateButton.setOnAction(event -> {
			if(selectedFigure != null) {
				DrawFigure newFigure = selectedFigure.duplicate(duplicateOffset);
				drawFigures.add(newFigure);
				canvasState.add(newFigure.getFigure());
				layersMap.get(newFigure.getLayer()).add(newFigure);
				redrawCanvas();
			}
		});

		divideButton.setOnAction(event -> {
			if(selectedFigure != null) {
				DrawFigure newFigure = selectedFigure.divide();
				drawFigures.add(newFigure);
				canvasState.add(newFigure.getFigure());
				layersMap.get(newFigure.getLayer()).add(newFigure);
				redrawCanvas();
			}
		});

		showLayerButton.setOnAction(event -> {
			layerChoiceBox.getValue().setVisible(true);
			redrawCanvas();
		});

		hideLayerButton.setOnAction(event -> {
			layerChoiceBox.getValue().setVisible(false);
			redrawCanvas();
		});

		frontButton.setOnAction(event -> {
			if(selectedFigure != null) {
				layersMap.get(layerChoiceBox.getValue()).remove(selectedFigure);
				layersMap.get(layerChoiceBox.getValue()).add(selectedFigure);
				redrawCanvas();
			}
		});

		backButton.setOnAction(event -> {
			if(selectedFigure != null) {
				layersMap.get(layerChoiceBox.getValue()).remove(selectedFigure);
				layersMap.get(layerChoiceBox.getValue()).addFirst(selectedFigure);
				redrawCanvas();
			}
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
			}
		});

		setLeft(buttonsBox);
		setRight(leftButtonsBox);
		setCenter(canvas);
	}

	void redrawCanvas() {
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

	/* ----------------------------------------- Funciones auxiliares -----------------------------------------*/

	// Inicializar los valores de los botones
	private void setFigureButtons(){
		circleButton.setUserData(new CircleButton());
		ellipseButton.setUserData(new EllipseButton());
		rectangleButton.setUserData(new RectangleButton());
		squareButton.setUserData(new SquareButton());
	}

}
