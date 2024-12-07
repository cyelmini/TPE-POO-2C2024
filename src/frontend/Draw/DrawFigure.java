package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

public abstract class DrawFigure {

    private final GraphicsContext gc;
    private Figure figure;
    private Color primaryColor, secondaryColor;
    private ShadowType shadowType;
    private boolean isBeveled;
    private final Layer layer;

    public DrawFigure(Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType,
                      boolean isBeveled, Layer layer){
        this.gc = gc;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowType = shadowType;
        this.isBeveled = isBeveled;
        this.layer = layer;
    }

    /* ------------------------------------------ Dibujo de figuras ------------------------------------------- */

    public abstract void draw(Figure selectedFigure, Color lineColor);

    public abstract void drawShadow();

    public abstract void drawBeveled();

    /* ---------------------------------------------- Getters ------------------------------------------------- */
    public GraphicsContext getGc(){
        return gc;
    }

    public Figure getFigure() {
        return figure;
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

    public Color getShadowColor(){
        return shadowType.getColor(primaryColor);
    }

    public double getOffset(){
        return shadowType.getOffset();
    }

    public Layer getLayer() {
        return layer;
    }

    /* ----------------------------------------------  Setters --------------------------------------------- */

    public void setFigure(Figure figure){
        this.figure = figure;
    }

    public void setPrimaryColor(Color color){
        this.primaryColor = color;
    }

    public void setSecondaryColor(Color color) {
        this.secondaryColor = color;
    }

    public void setColors(Color primaryColor, Color secondaryColor){
        setPrimaryColor(primaryColor);
        setSecondaryColor(secondaryColor);
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

    public void setShadowType(ShadowType shadowType){
        this.shadowType = shadowType;
    }

    public void setBeveled(boolean selected) {
        isBeveled = selected;
    }

    /* -------------------------------- Implementacion de las funcionalidades ------------------------------- */

    public boolean selected(Figure selectedFigure) {
        return figure.equals(selectedFigure);
    }

    public boolean isBeveled(){
        return isBeveled;
    }

    public boolean found(Point eventPoint) {
        return figure.found(eventPoint);
    }

    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    public void format(DrawFigure figure) {
        setColors(figure.getFillColor(), figure.getGradientColor());
        setShadowType(figure.getShadowType());
        setBeveled(figure.isBeveled());
    }

    public abstract void turnRight();

    public abstract void turnHorizontal();

    public abstract void turnVertical();

    public abstract DrawFigure duplicate(double offset);

    public abstract DrawFigure divide();

    /* -------------------------------------------------------------------------------------------------- */

    @Override
    public String toString(){
        return figure.toString();
    }

}