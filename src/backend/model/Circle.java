package backend.model;

public class Circle extends Ellipse {

    private final double radius;

    public Circle(Point centerPoint, double radius) {
        super(centerPoint,2*radius,2*radius);
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }

    @Override
    public boolean found(Point eventPoint){
        return Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < radius;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Circle circle &&
                getCenterPoint().equals(circle.getCenterPoint())
                && radius== circle.getRadius();
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", getCenterPoint(), radius);
    }

}
