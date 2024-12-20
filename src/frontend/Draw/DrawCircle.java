package frontend.Draw;

import backend.model.Circle;
import backend.model.Point;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawCircle extends DrawEllipse {

    public DrawCircle(Point centerPoint, double radius, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, boolean isBeveled, Layer layer) {
        super(centerPoint, radius*2, radius*2, primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        setFigure (new Circle(centerPoint, radius));
    }

}
