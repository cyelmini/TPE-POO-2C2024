package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawCircle;
import frontend.Draw.DrawFigure;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color color, GraphicsContext gc, ShadowType shadowType) {
        double radius = Math.abs(endPoint.getX() - startPoint.getX());
        return new DrawCircle(startPoint, radius, color, gc, shadowType);
    }
}
