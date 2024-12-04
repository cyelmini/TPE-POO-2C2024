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
//import jdk.internal.org.objectweb.asm.commons.JSRInlinerAdapter; ??


import java.util.*;

public class PaintPane extends BorderPane {

	/* ------------------------------------- BackEnd --------------------------------------------- */
	private CanvasState canvasState;

	/* --------------------------------- Canvas y relacionados ---------------------------------- */
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color defaultFillColor = Color.YELLOW;
	private Color defaultGradientColor = defaultFillColor;

	private double duplicateOffset = 10.0;

	/* ------------------------------------- Botones -------------------------------------------- */

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private ToggleButton circleButton = new ToggleButton("Círculo");
	private ToggleButton squareButton = new ToggleButton("Cuadrado");
	private ToggleButton ellipseButton = new ToggleButton("Elipse");
	private ToggleButton deleteButton = new ToggleButton("Borrar");

	// Botones para las sombras
	private ChoiceBox<ShadowType> shadowTypeChoiceBox = new ChoiceBox<>();

	// Botón checkbox biselado
	private CheckBox beveledCheckBox = new CheckBox("Biselado");

	// Botón selector de color de relleno
	private ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	//Botón selector de color de gradiente
	private ColorPicker gradientColorPicker = new ColorPicker(defaultGradientColor);

	// Botón copiar formato
	private ToggleButton copyFormatButton = new ToggleButton("Copiar fmt");

	//Botones Barra Derecha
	private ToggleButton turnRightButton = new ToggleButton("Girar D");
	private ToggleButton turnHorizontalButton = new ToggleButton("Voltear H");
	private ToggleButton turnVerticalButton = new ToggleButton("Voltear V");

	private ToggleButton duplicateButton = new ToggleButton("Duplicar");

	private ToggleButton divideButton = new ToggleButton("Dividir");

	
	// Botones Barra Superior
	private ChoiceBox<Layer> layerChoiceBox = new ChoiceBox<>();
	private ToggleButton frontButton = new ToggleButton("Traer al frente");

	private ToggleButton backButton = new ToggleButton("Enviar al fondo");

	private ToggleButton addLayerButton = new ToggleButton("Agregar capa");

	private ToggleButton removeLayerButton = new ToggleButton("Eliminar capa");

	/* ------------------------------------ Dibujo de figuras -------------------------------------- */

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	// Capas con drawFigures
	private Map<Layer, Map<Figure, DrawFigure>> figureLayers = new LinkedHashMap<>();
	
	
	public PaintPane(CanvasState canvasState, StatusPane statusPane) {

		this.canvasState = canvasState;
		this.statusPane = statusPane;

		/* ------------------------------------- Inicializar botones -----------------------------------*/

		// Inicializar botones de la izquierda
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton,
				deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		setFigureButtons();

		// Inicializar shadowbox
		shadowTypeChoiceBox.getItems().addAll(ShadowType.values());
		shadowTypeChoiceBox.setValue(ShadowType.NO_SHADOW); // Valor predeterminado
		shadowTypeChoiceBox.setPrefWidth(90);

		// Inicializar biselado
		beveledCheckBox.setPrefWidth(90);
		beveledCheckBox.setSelected(false);

		// Inicializa botón para copiar el formato
		copyFormatButton.setMinWidth(90);
		copyFormatButton.setCursor(Cursor.HAND);

		//Inicializa botones de la barra derecha
		ToggleButton[] actionsArr = {turnRightButton, turnHorizontalButton, turnVerticalButton, duplicateButton, divideButton};
		ToggleGroup actions = new ToggleGroup();
		for(ToggleButton action : actionsArr){
			action.setMinWidth(90);
			action.setToggleGroup(actions);
			action.setCursor(Cursor.HAND);
		}

		//Inicializa botones de la barra superior
		ToggleButton[] layersArr = {frontButton, backButton, addLayerButton, removeLayerButton};
		ToggleGroup layers = new ToggleGroup();
		for(ToggleButton layerButton : layersArr){
			layerButton.setMinWidth(90);
			layerButton.setToggleGroup(layers);
			layerButton.setCursor(Cursor.HAND);
		}
		for(int i = 1 ; i < 4 ; i++){
			layerChoiceBox.getItems().add(new Layer(i, "Capa " + i));
			figureLayers.put(layerChoiceBox.getItems().get(i-1), new LinkedHashMap<>());
		}
		layerChoiceBox.setValue(layerChoiceBox.getItems().get(0));
		layerChoiceBox.setPrefWidth(90);

		// Se crea un VBox para los botones de la barra izquierda
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(new Label("Formato"));
		buttonsBox.getChildren().addAll(shadowTypeChoiceBox, beveledCheckBox, fillColorPicker,
				gradientColorPicker, copyFormatButton);

		// Formato de la VBox izquierda
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// Se crea un Vbox para los botones de la barra derecha
		VBox leftButtonsBox = new VBox(10);
		leftButtonsBox.getChildren().add(new Label("Acciones:"));
		leftButtonsBox.getChildren().addAll(turnRightButton, turnHorizontalButton, turnVerticalButton,
				duplicateButton, divideButton);

		// Formato de la VBox derecha
		leftButtonsBox.setPadding(new Insets(5));
		leftButtonsBox.setStyle("-fx-background-color: #999");
		leftButtonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// Se crea un HBox para los botones de la barra superior
		HBox topButtonsBox = new HBox(10);
		topButtonsBox.getChildren().addAll(frontButton, backButton);
		topButtonsBox.getChildren().add(new Label("Capas"));
		topButtonsBox.getChildren().addAll(layerChoiceBox);
		topButtonsBox.getChildren().addAll(addLayerButton, removeLayerButton);

		//Formato de la HBox superior
		setTop(topButtonsBox);
		topButtonsBox.setAlignment(Pos.CENTER_LEFT);
		topButtonsBox.setPadding(new Insets(5));
		topButtonsBox.setStyle("-fx-background-color: #999");
		topButtonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		Region leftSpacer = new Region();
		Region rightSpacer = new Region();
		HBox.setHgrow(leftSpacer, Priority.ALWAYS); // Permite que los espaciadores crezcan para el espacio disponible
		HBox.setHgrow(rightSpacer, Priority.ALWAYS);
		topButtonsBox.getChildren().addFirst(leftSpacer);  // Agregar el espaciador izquierdo en la posición 0
		topButtonsBox.getChildren().add(rightSpacer);   // Agregar el espaciador derecho al final

		canvas.setOnMousePressed(event -> {
			startPoint = new Point(event.getX(), event.getY());
		});

		canvas.setOnMouseReleased(event -> {
			Point endPoint = new Point(event.getX(), event.getY());

			if(startPoint == null) { //imperativo: hay que hacer excepcion
				return ;
			}

			if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) { //idem anterior
				return ;
			}

			DrawFigure newFigure = null;
			Toggle selectedButton = tools.getSelectedToggle();

			if(selectedButton == null || selectedButton.getUserData() == null){
				return; // Ningún botón válido está seleccionado
			}

			Buttons button = (Buttons)selectedButton.getUserData();
			newFigure = button.getDrawFigure(startPoint, endPoint, fillColorPicker.getValue(), gradientColorPicker.getValue(), gc, shadowTypeChoiceBox.getValue(), beveledCheckBox.isSelected());
			figureLayers.get(layerChoiceBox.getValue()).put(newFigure.getFigure(), newFigure);
			canvasState.addFigure(newFigure.getFigure());
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(Layer layer : figureLayers.keySet()){
				for(DrawFigure drawFigure : figureLayers.get(layer).values()){
					// verificar que no detecta figuras invisibles, si no agregar eso
					if(drawFigure.found(eventPoint)){
						found = true;
						label.append(drawFigure);
					}
				}
			}
			if(found) {
				statusPane.updateStatus(label.toString());
			} else {
				statusPane.updateStatus(eventPoint.toString());
			}
		});

//		JSRInlinerAdapter.Instantiation drawFigures; ?????????? q es esto
		canvas.setOnMouseClicked(event -> {
			if(selectionButton.isSelected()) {
				Point eventPoint = new Point(event.getX(), event.getY());
				boolean found = false;
				StringBuilder label = new StringBuilder("Se seleccionó: ");
				for(Layer layer : figureLayers.keySet()) {
					for (DrawFigure drawFigure : figureLayers.get(layer).values()) {
						if (drawFigure.found(eventPoint)) {
							if (selectedFigure != null && copyFormatButton.isSelected()) {
								drawFigure.format(figureLayers.get(layer).get(selectedFigure));
								copyFormatButton.setSelected(false);
							} else {
								selectedFigure = drawFigure.getFigure();
								fillColorPicker.setValue(drawFigure.getFillColor());
								gradientColorPicker.setValue(drawFigure.getGradientColor());
								shadowTypeChoiceBox.setValue(drawFigure.getShadowType());
								beveledCheckBox.setSelected(drawFigure.isBeveled());
								label.append(drawFigure);
								found = true;
							}
						}
					}
				}
				if (found) {
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
				
				for(Layer layer : figureLayers.keySet()){
					if(figureLayers.get(layer).containsKey(selectedFigure)){
						figureLayers.get(layer).get(selectedFigure).move(diffX, diffY);
					}
				}

				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			for(Layer layer : figureLayers.keySet()){
				if(figureLayers.get(layer).containsKey(selectedFigure)){
					figureLayers.get(layer).remove(selectedFigure);
					canvasState.deleteFigure(selectedFigure);
					selectedFigure = null;
					redrawCanvas();
					return;
				}
			}
		});

		fillColorPicker.setOnAction(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).setPrimaryColor(fillColorPicker.getValue());
			redrawCanvas();
		});

		gradientColorPicker.setOnAction(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).setSecondaryColor(gradientColorPicker.getValue());
			redrawCanvas();
		});

		shadowTypeChoiceBox.setOnAction(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).setShadowType(shadowTypeChoiceBox.getValue());
			redrawCanvas();
		});

		beveledCheckBox.setOnAction(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).setBeveled(beveledCheckBox.isSelected());
			redrawCanvas();
		});

		turnRightButton.setOnMouseClicked(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).turnRight();
			redrawCanvas();
		});

		turnHorizontalButton.setOnMouseClicked(event ->{
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).turnHorizontal();
			redrawCanvas();
		});

		turnVerticalButton.setOnMouseClicked(event -> {
			Layer temp = validateSelectedFigureLayer();
			figureLayers.get(temp).get(selectedFigure).turnVertical();
			redrawCanvas();
		});

		duplicateButton.setOnAction(event -> {
			Layer temp  = validateSelectedFigureLayer();
			DrawFigure newFigure = figureLayers.get(temp).get(selectedFigure).duplicate(duplicateOffset);
			figureLayers.get(temp).put(newFigure.getFigure(), newFigure);
			canvasState.addFigure(newFigure.getFigure());
			redrawCanvas();
		});

		divideButton.setOnAction(event -> {
			Layer temp  = validateSelectedFigureLayer();
			DrawFigure newFigure = figureLayers.get(temp).get(selectedFigure).divide();
			figureLayers.get(temp).put(newFigure.getFigure(), newFigure);
			canvasState.addFigure(newFigure.getFigure());
			redrawCanvas();
		});

		setLeft(buttonsBox);
		setRight(leftButtonsBox);
		setCenter(canvas);
	}
	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for(Layer layer : figureLayers.keySet()){
			if(layer.isVisible()){
				for(DrawFigure drawFigure : figureLayers.get(layer).values()){
					drawFigure.draw(selectedFigure, lineColor);
				};
			}
		}
	}

	/* ------------------------------------ Funciones auxiliares ---------------------------------*/

	// Inicializar los valores de los botones
	private void setFigureButtons(){
		circleButton.setUserData(new CircleButton());
		ellipseButton.setUserData(new EllipseButton());
		rectangleButton.setUserData(new RectangleButton());
		squareButton.setUserData(new SquareButton());
	}

	// Validaciones de selección de figuras
	public Layer validateSelectedFigureLayer() {
		for(Layer layer : figureLayers.keySet()){
			if(selectionButton.isSelected() && selectedFigure != null && figureLayers.get(layer).containsKey(selectedFigure)){
				return layer;
			}
		}
		throw new RuntimeException("No se ha seleccionado ninguna figura");
	}

}
