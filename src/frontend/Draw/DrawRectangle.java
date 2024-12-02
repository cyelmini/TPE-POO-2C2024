package frontend.Draw;

import backend.model.Point;
import backend.model.Rectangle;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawRectangle(Point topLeft, Point bottomRight, Color color, GraphicsContext gc, ShadowType shadowType){
        super(color, gc, shadowType);
        figure = new Rectangle(topLeft, bottomRight);
        rectangle = (Rectangle)figure;

    }

    public void draw(){

        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            gc.setFill(shadowColor);
            double offset = getOffset();
            gc.fillRect(rectangle.getTopLeft().getX() + offset, rectangle.getTopLeft().getY() + offset, rectangle.width(), rectangle.height());
        }

        gc.setFill(getFillColor());
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
    }

}
