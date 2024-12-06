package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawEllipse;
import frontend.Draw.DrawFigure;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color primaryColor, Color secondaryColor,
                                    GraphicsContext gc, ShadowType shadowType, boolean isBeveled, Layer layer) {
        Point centerPoint = new Point(Math.abs(endPoint.getX() + startPoint.getX()) / 2, (Math.abs((endPoint.getY() + startPoint.getY())) / 2));
        double sMayorAxis = Math.abs(endPoint.getX() - startPoint.getX());
        double sMinorAxis = Math.abs(endPoint.getY() - startPoint.getY());
        return new DrawEllipse(centerPoint, sMayorAxis, sMinorAxis, primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
    }
}
