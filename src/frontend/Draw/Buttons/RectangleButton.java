package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.Draw.DrawRectangle;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color primaryColor, Color secondaryColor,
                                    GraphicsContext gc, ShadowType shadowType, boolean isBeveled, Layer layer) {
        return new DrawRectangle(startPoint, endPoint, primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
    }
}
