package backend.model;

public class Square extends Rectangle {

    private final double size;

    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.x + size, topLeft.y + size));
        this.size = size;
    }


    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
