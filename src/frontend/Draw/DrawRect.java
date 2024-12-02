package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRect extends DrawFigure {

    private Rectangle rectangle;

    public DrawRect(Point topLeft, Point bottomRight, Color color, GraphicsContext gc, ShadowType shadowType){
        super(color, gc,shadowType);

    }

    public void draw(){
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
    }
}
