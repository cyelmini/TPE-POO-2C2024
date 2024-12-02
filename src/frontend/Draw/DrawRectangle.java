package frontend.Draw;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {

    private Rectangle rectangle;

    public DrawRectangle(Point topLeft, Point bottomRight, Color color, GraphicsContext gc){
        super(color, gc);
        figure = new Rectangle(topLeft, bottomRight);
        rectangle = (Rectangle)figure;

    }

    public void draw(){
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
    }
}
