package frontend;

import backend.model.Figure;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawFigure {

    GraphicsContext gc;
    private Figure figure;
    private Color color;

    public DrawFigure(Figure figure, Color color, GraphicsContext gc){
        this.figure = figure;
        this.color = color;
        this.gc = gc;

    }

    public void draw();

    public Figure getFigure() {
        return figure;
    }

}
