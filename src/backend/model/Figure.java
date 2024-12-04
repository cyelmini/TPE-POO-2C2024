package backend.model;

public abstract class Figure {



    public abstract Figure divideFigure();

    public abstract double width();

    public abstract double height();

    public abstract boolean found(Point eventPoint);

    public abstract void move(double diffX, double diffY);

    // Métodos para voltear las figuras
    public abstract void turnRight();

    public abstract void turnHorizontal();

    public abstract void turnVertical();

    public abstract boolean equals(Object o);

}
