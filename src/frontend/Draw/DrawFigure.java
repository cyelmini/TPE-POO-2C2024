package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class DrawFigure {

    protected GraphicsContext gc;
    protected Figure figure;
    private Color color;

    public DrawFigure(Color color, GraphicsContext gc){
        this.color = color;
        this.gc = gc;
    }

    public abstract void draw();

    public Figure getFigure() {
        return figure;
    }

    public boolean selected(Figure selectedFigure) {
        return figure.equals(selectedFigure);
    }

    public Paint getFillColor() {
        return color;
    }

    public boolean found(Point eventPoint) {
        return figure.found(eventPoint);
    }

    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    public String toString(){
        return figure.toString();
    }

}
