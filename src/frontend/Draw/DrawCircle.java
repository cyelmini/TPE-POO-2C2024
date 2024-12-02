package frontend.Draw;

import backend.model.Circle;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawEllipse {

    private final Circle circle;

    public DrawCircle(Point centerPoint, double radius, Color color, GraphicsContext gc, ShadowType shadowType) {
        super(centerPoint, radius*2, radius*2, color, gc, shadowType);
        figure = new Circle(centerPoint, radius);
        circle = (Circle) figure;
    }

}
