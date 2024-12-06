package backend.model;

public class Rectangle extends Figure {

    private Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /* -----------------------------------  Calculos de propiedades de la figura ------------------------------ */
    @Override
    public double width(){
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    @Override
    public double height(){
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    /* --------------------------------------------- Getters ------------------------------------------------- */
    public Point getTopLeft(){
        return topLeft;
    }

    public Point getBottomRight(){
        return bottomRight;
    }

    /* ------------------------------- Implementacion de las funcionalidades ------------------------------- */

    @Override
    public boolean found(Point eventPoint){
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }

    @Override
    public void move(double diffX, double diffY){
        topLeft.move(diffX, diffY);
        bottomRight.move(diffX, diffY);
    }

    @Override
    public void turnRight() {
        double centerX = (topLeft.getX() + bottomRight.getX()) / 2;
        double centerY = (topLeft.getY() + bottomRight.getY()) / 2;

        double currentWidth = width();
        double currentHeight = height();

        Point newTopLeft = new Point(
                centerX - currentHeight / 2,
                centerY - currentWidth / 2
        );

        Point newBottomRight = new Point(
                centerX + currentHeight / 2,
                centerY + currentWidth / 2
        );

        this.topLeft = newTopLeft;
        this.bottomRight = newBottomRight;
    }

    @Override
    public void turnHorizontal(){
        double originalWidth = width();
        topLeft.setX(topLeft.getX() + originalWidth);
        bottomRight.setX(bottomRight.getX() + originalWidth);
    }

    @Override
    public void turnVertical(){
        double originalHeight = height();
        topLeft.setY(topLeft.getY() + originalHeight);
        bottomRight.setY(bottomRight.getY() + originalHeight);
    }

    @Override
    public Rectangle divideFigure() {
        double centerX = (topLeft.getX() + bottomRight.getX()) / 2;
        double centerY = (topLeft.getY() + bottomRight.getY()) / 2;
        double newHalfHeight = height()/4;

        // Crear un nuevo rectangulo con el punto centro y el punto original bottomRight
        Rectangle newRectangle = new Rectangle(
                new Point(centerX, centerY - newHalfHeight),
                new Point(bottomRight.getX(), centerY + newHalfHeight)
        );

        // Actualizar el bottomRight del rectangulo actual al punto centro
        topLeft = new Point(topLeft.getX(), centerY - newHalfHeight);
        bottomRight = new Point(centerX, centerY + newHalfHeight);

        return newRectangle;
    }

    /* -------------------------------------------------------------------------------------------------- */

    @Override
    public boolean equals(Object o){
        return o instanceof Rectangle rectangle
                && topLeft.equals(rectangle.getTopLeft())
                && bottomRight.equals(rectangle.getBottomRight());
    }

    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

}
