package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

public abstract class DrawFigure {

    protected GraphicsContext gc;
    protected Figure figure;
    private Color primaryColor, secondaryColor;
    private ShadowType shadowType;
    private boolean isBeveled;

    public DrawFigure(Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, boolean isBeveled){
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.gc = gc;
        this.shadowType = shadowType;
        this.isBeveled = isBeveled;
    }

    public abstract void draw(Figure selectedFigure, Color lineColor);

    public abstract void drawShadow();

    public abstract void drawBeveled();

    public Figure getFigure() {
        return figure;
    }
    public GraphicsContext getGc(){
        return gc;
    }

    public boolean selected(Figure selectedFigure) {
        return figure.equals(selectedFigure);
    }

    public Color getFillColor() {
        return primaryColor;
    }
    public Color getGradientColor(){
        return secondaryColor;
    }


    public ShadowType getShadowType() {
        return shadowType;
    }

    public boolean isBeveled(){
        return isBeveled;
    }

    public void setGradientRad(){
        RadialGradient radialGradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, primaryColor),
                new Stop(1, secondaryColor));
        gc.setFill(radialGradient);
    }

    public void setGradientLinear(){
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, primaryColor),
                new Stop(1, secondaryColor));
        gc.setFill(linearGradient);
    }

    public void setColors(Color primaryColor, Color secondaryColor){
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public void setShadowType(ShadowType shadowType){
        this.shadowType = shadowType;
    }

    public boolean found(Point eventPoint) {
        return figure.found(eventPoint);
    }

    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    public Color getShadowColor(){
        return shadowType.getColor(primaryColor);
    }

    public double getOffset(){
        return shadowType.getOffset();
    }

    public String toString(){
        return figure.toString();
    }

    public void setBeveled(boolean selected) {
        isBeveled = selected;
    }

    public void format(DrawFigure figure) {
        setColors(figure.getFillColor(), figure.getGradientColor());
        setShadowType(figure.getShadowType());
        setBeveled(figure.isBeveled());
    }
}
