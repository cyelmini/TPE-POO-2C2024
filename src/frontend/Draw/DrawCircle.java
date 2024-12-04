package frontend.Draw;

import backend.model.Circle;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawEllipse {

    private final Circle circle;

    public DrawCircle(Point centerPoint, double radius, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, boolean isBeveled) {
        super(centerPoint, radius*2, radius*2, primaryColor, secondaryColor, gc, shadowType, isBeveled);
        figure = new Circle(centerPoint, radius);
        circle = (Circle) figure;

    }

}
