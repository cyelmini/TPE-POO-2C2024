package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Buttons {
    DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color primaryColor, Color secondaryColor,
                             GraphicsContext gc, ShadowType shadowType, boolean isBeveled);
}
