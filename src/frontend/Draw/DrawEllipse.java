package frontend.Draw;

import backend.model.Ellipse;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color color, GraphicsContext gc) {
        super(color, gc);
        figure = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
        ellipse = (Ellipse) figure;
    }

    @Override
    public void draw() {
        gc.strokeOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            gc.setFill(shadowColor);
            double offset = getOffset();
            gc.fillOval(ellipse.width() + offset, ellipse.height() + offset, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        }

    }
}
