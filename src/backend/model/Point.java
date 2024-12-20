package backend.model;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    public void move(double diffX, double diffY){
        x += diffX;
        y += diffY;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Point point
                && x == point.getX()
                && y == point.getY();
    }

}
