package frontend.Draw.Buttons;

import backend.model.Point;
import frontend.Draw.DrawFigure;
import frontend.Draw.DrawRectangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleButton implements Buttons{
    @Override
    public DrawFigure getDrawFigure(Point startPoint, Point endPoint, Color color, GraphicsContext gc) {
        return new DrawRectangle(startPoint, endPoint, color, gc);
    }

}
