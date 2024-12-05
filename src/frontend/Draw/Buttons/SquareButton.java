package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.Draw.DrawSquare;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareButton implements Buttons{

    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color primaryColor, Color secondaryColor,
                                    GraphicsContext gc, ShadowType shadowType, boolean isBeveled) {

        double size = Math.abs(startPoint.getX() - endPoint.getX());
        return new DrawSquare(startPoint, primaryColor, secondaryColor, gc, shadowType, size, isBeveled);

    }

}
