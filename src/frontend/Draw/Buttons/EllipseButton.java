package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawEllipse;
import frontend.Draw.DrawFigure;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color color, GraphicsContext gc, ShadowType shadowType) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new DrawEllipse(centerPoint, sMayorAxis, sMinorAxis, color, gc, shadowType);
    }
}
