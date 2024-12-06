package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawRectangle {

    private final Square square;

    public DrawSquare(Point topLeft, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, double size, boolean isBeveled, Layer layer) {
        super(topLeft, new Point(topLeft.getX()+ size, topLeft.getY() + size), primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        setFigure (new Square(topLeft, size));
        square = (Square)getFigure();
    }

    public Rectangle getRectangle(){
        return square;
    }

}
