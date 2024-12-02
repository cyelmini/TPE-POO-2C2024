package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.ShadowType;
import javafx.scene.paint.Color;

public interface Buttons {
    DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color color, ShadowType shadowType);
}
