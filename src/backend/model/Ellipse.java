package backend.model;

public class Ellipse extends Figure {

    protected Point centerPoint;
    protected double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    // Cálculos de propiedades de las figuras
    @Override
    public double width(){
        return centerPoint.getX() - (sMayorAxis / 2);
    }

    @Override
    public double height(){
        return centerPoint.getY() - (sMinorAxis / 2);
    }

    // Getters
    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public boolean found(Point eventPoint){
         return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public void move(double diffX, double diffY) {
        centerPoint.move(diffX, diffY);
    }

    // Métodos para voltear las figuras
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
