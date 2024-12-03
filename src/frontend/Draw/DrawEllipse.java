package frontend.Draw;

import backend.model.Ellipse;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType) {
        super(primaryColor, secondaryColor, gc, shadowType);
        figure = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
        ellipse = (Ellipse) figure;
    }

    @Override
    public void draw() {

        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            gc.setFill(shadowColor);
            double offset = getOffset();
            gc.fillOval(ellipse.width() + offset, ellipse.height() + offset, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        }

        setGradientRad();
        gc.strokeOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.fillOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

    }
}
