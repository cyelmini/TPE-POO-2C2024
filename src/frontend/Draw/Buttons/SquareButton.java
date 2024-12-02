package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.Draw.DrawSquare;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color color, GraphicsContext gc, ShadowType shadowType) {
        return new DrawSquare(startPoint, endPoint, color, gc, shadowType);
    }
}
