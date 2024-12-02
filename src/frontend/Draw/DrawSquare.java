package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawRect {

    private Square square;

    public DrawSquare(Point topLeft, Point bottomRight, Color color, GraphicsContext gc, ShadowType shadowType) {
        super(topLeft, bottomRight, color, gc, shadowType);

    }

    @Override
    public void draw() {
        gc.fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                square.width(), square.height());
        gc.strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                square.width(), square.height());
    }
}
