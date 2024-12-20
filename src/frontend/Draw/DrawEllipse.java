package frontend.Draw;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class DrawEllipse extends DrawFigure {

    private final Ellipse ellipse;

    public DrawEllipse(Point centerPoint, double sMayorAxis, double sMinorAxis, Color primaryColor, Color secondaryColor,
                       GraphicsContext gc, ShadowType shadowType, boolean isBeveled, Layer layer) {
        super(primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        setFigure(new Ellipse(centerPoint, sMayorAxis, sMinorAxis));
        ellipse = (Ellipse)getFigure();
    }

    @Override
    public void draw(Figure selectedFigure, Color lineColor) {
        drawShadow();

        drawBeveled();

        setGradientRad();

        getGc().fillOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());

        if(selected(selectedFigure)) {
            getGc().setStroke(Color.RED);
        } else {
            getGc().setStroke(lineColor);
        }
        getGc().strokeOval(ellipse.width(), ellipse.height(), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    public void drawShadow(){
        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            getGc().setFill(shadowColor);
            double offset = getOffset();
            getGc().fillOval(ellipse.width() + offset, ellipse.height() + offset, ellipse.getsMayorAxis(),
                    ellipse.getsMinorAxis());
        }
    }

    @Override
    public void drawBeveled(){
        if(isBeveled()){
            double arcX = ellipse.width();
            double arcY = ellipse.height();

            getGc().setLineWidth(10);
            getGc().setStroke(Color.LIGHTGRAY);
            getGc().strokeArc(arcX, arcY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis(), 45, 180, ArcType.OPEN);
            getGc().setStroke(Color.BLACK);
            getGc().strokeArc(arcX, arcY, ellipse.getsMayorAxis(), ellipse.getsMinorAxis(), 225, 180, ArcType.OPEN);
            getGc().setLineWidth(1);
        }
    }

    @Override
    public void turnRight(){
        ellipse.turnRight();
    }

    @Override
    public void turnHorizontal(){
        ellipse.turnHorizontal();
    }

    @Override
    public void turnVertical(){
        ellipse.turnVertical();
    }

    public DrawEllipse duplicate(double offset){
        Point duplicatedCenterPoint = new Point(ellipse.getCenterPoint().getX(), ellipse.getCenterPoint().getY());
        duplicatedCenterPoint.move(offset, offset);
        return new DrawEllipse(duplicatedCenterPoint, ellipse.getsMayorAxis(), ellipse.getsMinorAxis(), getFillColor(),
                getGradientColor(), getGc(), getShadowType(), isBeveled(), getLayer());
    }

    public DrawEllipse divide(){
        Ellipse temp = ellipse.divideFigure();
        return new DrawEllipse(temp.getCenterPoint(), temp.getsMayorAxis(), temp.getsMinorAxis(), getFillColor(),
                getGradientColor(), getGc(), getShadowType(), isBeveled(), getLayer());
    }

}
