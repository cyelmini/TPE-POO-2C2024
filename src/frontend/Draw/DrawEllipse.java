package frontend.Draw;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipse extends DrawFigure {

    private Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color, GraphicsContext gc, ShadowType shadowType) {
        super(color, gc, shadowType);

    }

    @Override
    public void draw() {
        gc.strokeOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
}
