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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.*;

public class PaintPane extends BorderPane {

	// BackEnd
	private CanvasState canvasState;

	// Canvas y relacionados
	private Canvas canvas = new Canvas(800, 600);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private Color lineColor = Color.BLACK;
	private Color defaultFillColor = Color.YELLOW;
	private Color defaultGradientColor = defaultFillColor;

	// Botones Barra Izquierda
	private ToggleButton selectionButton = new ToggleButton("Seleccionar");
	private ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	private ToggleButton circleButton = new ToggleButton("Círculo");
	private ToggleButton squareButton = new ToggleButton("Cuadrado");
	private ToggleButton ellipseButton = new ToggleButton("Elipse");
	private ToggleButton deleteButton = new ToggleButton("Borrar");

	// Botones para las sombras
	private ChoiceBox<ShadowType> shadowTypeChoiceBox = new ChoiceBox<>();

	// Checkbox biselado
	private CheckBox beveledCheckBox = new CheckBox("Biselado");

	// Botón copiar formato
	private ToggleButton copyFormatButton = new ToggleButton("Copiar fmt");

	// Selector de color de relleno
	private ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	//Selector de color de gradiente
	private ColorPicker gradientColorPicker = new ColorPicker(defaultGradientColor);

	// Dibujar una figura
	private Point startPoint;

	// Seleccionar una figura
	private Figure selectedFigure;

	// StatusBar
	private StatusPane statusPane;

	// Lista de DrawFigures
	private Map<Figure, DrawFigure> drawFigures = new LinkedHashMap<>();
	
	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Inicializar shadowbox
		shadowTypeChoiceBox.getItems().addAll(ShadowType.values());
		shadowTypeChoiceBox.setValue(ShadowType.NO_SHADOW); // Valor predeterminado
		shadowTypeChoiceBox.setPrefWidth(90);

		// Inicializar biselado
		beveledCheckBox.setPrefWidth(90);
		beveledCheckBox.setSelected(false);

		// Cuando un botón es seleccionado por el usuario, cualquier otro botón que esté activado dentro
		// del grupo tools se desactivará automáticamente.
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton,
				deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		setFigureButtons();

		// Inicializa botón para copiar el formato
		copyFormatButton.setMinWidth(90);
		copyFormatButton.setCursor(Cursor.HAND);

		// Se crea un VBox para los botones de la barra izquierda
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(new Label("Formato"));
		buttonsBox.getChildren().add(shadowTypeChoiceBox);
		buttonsBox.getChildren().add(beveledCheckBox);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(gradientColorPicker);
		buttonsBox.getChildren().add(copyFormatButton);

		// Formato de la VBox
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);


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
			newFigure = button.getDrawFigure(startPoint, endPoint, fillColorPicker.getValue(), gradientColorPicker.getValue(),
												gc, shadowTypeChoiceBox.getValue(), beveledCheckBox.isSelected());
			drawFigures.put(newFigure.getFigure(), newFigure);
			canvasState.addFigure(newFigure.getFigure());
			startPoint = null;
			redrawCanvas();
		});

		canvas.setOnMouseMoved(event -> {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder();
			for(DrawFigure drawFigure : drawFigures.values()) {
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
				for(DrawFigure drawFigure : drawFigures.values()) {
					if(drawFigure.found(eventPoint)) {
						selectedFigure = drawFigure.getFigure();
						fillColorPicker.setValue(drawFigure.getFillColor());
						gradientColorPicker.setValue(drawFigure.getGradientColor());
						shadowTypeChoiceBox.setValue(drawFigure.getShadowType());
						beveledCheckBox.setSelected(drawFigure.isBeveled());
						label.append(drawFigure);
						found = true;
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

				if (drawFigures.containsKey(selectedFigure)) {
					drawFigures.get(selectedFigure).move(diffX, diffY);
				}

				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if(drawFigures.containsKey(selectedFigure)){
				drawFigures.remove(selectedFigure);
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		fillColorPicker.setOnAction(event -> {
			if(selectionButton.isSelected()) {
				changeSelectedFigureColor();
			}
		});

		gradientColorPicker.setOnAction(event -> {
			if(selectionButton.isSelected()) {
				changeSelectedFigureColor();
			}
		});

		shadowTypeChoiceBox.setOnAction(event -> {
			if(selectionButton.isSelected() && drawFigures.containsKey(selectedFigure)){
				drawFigures.get(selectedFigure).setShadowType(shadowTypeChoiceBox.getValue());
				redrawCanvas();
			}
		});

		beveledCheckBox.setOnAction(event -> {
			if(selectionButton.isSelected() && drawFigures.containsKey(selectedFigure)){
				drawFigures.get(selectedFigure).setBeveled(beveledCheckBox.isSelected());
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void changeSelectedFigureColor() {
		if(drawFigures.containsKey(selectedFigure)){
			drawFigures.get(selectedFigure).setColors(fillColorPicker.getValue(), gradientColorPicker.getValue());
			redrawCanvas();
		}
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		for(DrawFigure drawFigure : drawFigures.values()){
			drawFigure.draw(selectedFigure, lineColor);
		}
	}

	// Función para inicializar los valores de los botones
	private void setFigureButtons(){
		circleButton.setUserData(new CircleButton());
		ellipseButton.setUserData(new EllipseButton());
		rectangleButton.setUserData(new RectangleButton());
		squareButton.setUserData(new SquareButton());
	}

}
