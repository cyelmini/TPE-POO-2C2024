package backend.model;

public class Square extends Rectangle {

    private final double size;

    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.getX() + size, topLeft.getY() + size));
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Square square &&
                size == square.size
                && getTopLeft().equals(square.getTopLeft());
    }
}
