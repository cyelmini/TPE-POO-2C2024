package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawRectangle {

    private final Square square;

    public DrawSquare(Point topLeft, Point bottomRight, Color color, GraphicsContext gc, ShadowType shadowType) {
        super(topLeft, bottomRight, color, gc, shadowType);
        double size = Math.abs(topLeft.getX() - bottomRight.getX());
        figure = new Square(topLeft, size);
        square = (Square)figure;
    }

}
