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
        this.gc = gc;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowType = shadowType;
        this.isBeveled = isBeveled;
    }

    // Métodos de dibujo
    public abstract void draw(Figure selectedFigure, Color lineColor);

    public abstract void drawShadow();

    public abstract void drawBeveled();

    //Getters
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

    // Setters
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

    // Método que devuelve true si la figura dada es la figura
    // de la drawFigure
    public boolean selected(Figure selectedFigure) {
        return figure.equals(selectedFigure);
    }


    public boolean isBeveled(){
        return isBeveled;
    }

    // Metodo que devuelve true si un determinado punto está dentro de
    // una figura
    public boolean found(Point eventPoint) {
        return figure.found(eventPoint);
    }

    // Método para mover la figura
    public void move(double diffX, double diffY){
        figure.move(diffX, diffY);
    }

    // Método que recibe un drawFigure y copia su formato
    public void format(DrawFigure figure) {
        setColors(figure.getFillColor(), figure.getGradientColor());
        setShadowType(figure.getShadowType());
        setBeveled(figure.isBeveled());
    }

    // Métodos para voltear figuras
    public abstract void turnRight();

    public abstract void turnHorizontal();

    public abstract void turnVertical();

    public abstract DrawFigure duplicate(double offset);

//    public abstract void divide();

    // To string
    public String toString(){
        return figure.toString();
    }

}