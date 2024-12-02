package backend.model;

public abstract class Figure {

    public abstract boolean found(Point eventPoint);

    public abstract void move(double diffX, double diffY);

    public abstract boolean equals(Object o);

}
