package frontend.Draw;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawFigure {

    private Circle circle;

    public DrawCircle(Point centerPoint, double radius, Color color, GraphicsContext gc, ShadowType shadowType) {
        super(color, gc, shadowType);
    }

    @Override
    public void draw() {
        double diameter = circle.getRadius() * 2;
        gc.fillOval(circle.width(), circle.height(), diameter, diameter);
        gc.strokeOval(circle.width(), circle.height(), diameter, diameter);
    }
}
