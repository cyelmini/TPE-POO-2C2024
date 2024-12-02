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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");
	ToggleButton deleteButton = new ToggleButton("Borrar");

	// Botones para las sombras
	private ChoiceBox<ShadowType> shadowTypeChoiceBox = new ChoiceBox<>();

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	// Lista de DrawFigures
	List<DrawFigure> drawFigures = new ArrayList<>();


	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		// Cuando un botón es seleccionado por el usuario, cualquier otro botón que esté activado dentro
		// del grupo tools se desactivará automáticamente.
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		// Se setean los valores de los botones a usar
		setButtons();

		shadowTypeChoiceBox.getItems().addAll(ShadowType.values());
		shadowTypeChoiceBox.setValue(ShadowType.NO_SHADOW); // Valor predeterminado
		shadowTypeChoiceBox.setPrefWidth(90);
		buttonsBox.getChildren().add(shadowTypeChoiceBox);

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
			newFigure = button.getDrawFigure(startPoint, endPoint, fillColorPicker.getValue(), gc);
			newFigure.setShadowType(shadowTypeChoiceBox.getValue());
			drawFigures.add(newFigure);
			canvasState.addFigure(newFigure.getFigure());
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
						selectedFigure = drawFigure.getFigure();
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

				for(DrawFigure drawFigure : drawFigures){
					if(drawFigure.getFigure().equals(selectedFigure)){
						drawFigure.move(diffX, diffY);
					}
				}
				redrawCanvas();
			}
		});

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				drawFigures.removeIf(drawFigure -> drawFigure.selected(selectedFigure));
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(DrawFigure drawFigure : drawFigures){
			if(drawFigure.selected(selectedFigure)) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(drawFigure.getFillColor());
			drawFigure.draw();
		}
	}

	// Función para inicializar los valores de los botones
	private void setButtons(){
		circleButton.setUserData(new CircleButton());
		ellipseButton.setUserData(new EllipseButton());
		rectangleButton.setUserData(new RectangleButton());
		squareButton.setUserData(new SquareButton());
	}

}
