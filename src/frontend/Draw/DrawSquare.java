package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawRectangle {

    private Square square;

    public DrawSquare(Point topLeft, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, double size, boolean isBeveled, Layer layer) {
        super(topLeft, new Point(topLeft.getX()+ size, topLeft.getY() + size), primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        figure = new Square(topLeft, size);
        square = (Square)getFigure();

    }

}
