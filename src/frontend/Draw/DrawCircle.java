package frontend.Draw;

import backend.model.Circle;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawEllipse {

    private final Circle circle;

    public DrawCircle(Point centerPoint, double radius, Color color, GraphicsContext gc) {
        super(centerPoint, radius*2, radius*2, color, gc);
        figure = new Circle(centerPoint, radius);
        circle = (Circle) figure;
    }
//
//    @Override
//    public void draw() {
//        double diameter = circle.getRadius() * 2;
//        gc.fillOval(circle.width(), circle.height(), diameter, diameter);
//        gc.strokeOval(circle.width(), circle.height(), diameter, diameter);
//    }

}
