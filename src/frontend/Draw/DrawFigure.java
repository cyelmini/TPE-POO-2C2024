package frontend.Draw;

import backend.model.Figure;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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

}
