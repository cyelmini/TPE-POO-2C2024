package backend.model;

public class Ellipse extends Figure {

    private Point centerPoint;
    private double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    /* -------------------------------- Calculos de propiedades de las figuras ------------------------------ */
    @Override
    public double width(){
        return centerPoint.getX() - (sMayorAxis / 2);
    }

    @Override
    public double height(){
        return centerPoint.getY() - (sMinorAxis / 2);
    }

    /* -----------------------------------------  Getters -------------------------------------------------- */
    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    /* -------------------------------- Implementacion de las funcionalidades ----------------------------- */

    @Override
    public boolean found(Point eventPoint){
         return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void move(double diffX, double diffY) {
        centerPoint.move(diffX, diffY);
    }

    @Override
    public void turnRight() {
        double aux = sMayorAxis;
        sMayorAxis = sMinorAxis;
        sMinorAxis = aux;
    }

    @Override
    public void turnHorizontal(){
        centerPoint.setX(centerPoint.getX() + sMayorAxis);
    }

    @Override
    public void turnVertical(){
        centerPoint.setY(centerPoint.getY() + sMinorAxis);
    }

    @Override
    public Ellipse divideFigure() {
        double newSMinorAxis = sMinorAxis / 2;
        double newSMayorAxis = sMayorAxis / 2;
        double newCenterX = centerPoint.getX();
        double newCenterY = centerPoint.getY();

        // Se actualizan las figuras
        this.centerPoint = new Point(newCenterX-newSMayorAxis/2, newCenterY);
        this.sMinorAxis = newSMinorAxis;
        this.sMayorAxis = newSMayorAxis;

        // Se retorna la nuevas figuras
        return new Ellipse(new Point(newCenterX+newSMayorAxis/2, newCenterY), newSMayorAxis, newSMinorAxis);
    }

    /* --------------------------------------------------------------------------------------------------------- */

    @Override
    public boolean equals(Object o){
        return o instanceof Ellipse ellipse &&
                sMayorAxis == ellipse.getsMayorAxis() &&
                sMinorAxis == ellipse.getsMinorAxis() &&
                centerPoint.equals(ellipse.getCenterPoint());
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

}
