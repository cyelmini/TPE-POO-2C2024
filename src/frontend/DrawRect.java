package frontend;


import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRect extends DrawFigure {
    public DrawRect(Rectangle figure, Color color, GraphicsContext gc){
        super(figure, color, gc);
    }

    public void draw(){
        gc.fillRect(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(),
                Math.abs(getFigure().getTopLeft().getX() - getFigure().getBottomRight().getX()), Math.abs(getFigure().getTopLeft().getY() - getFigure().getBottomRight().getY()));
        gc.strokeRect(getFigure().getTopLeft().getX(), getFigure().getTopLeft().getY(),
                Math.abs(getFigure().getTopLeft().getX() - getFigure().getBottomRight().getX()), Math.abs(getFigure().getTopLeft().getY() - getFigure().getBottomRight().getY()));
    }
}
