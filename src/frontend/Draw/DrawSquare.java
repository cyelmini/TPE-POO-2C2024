package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import backend.model.Square;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawSquare extends DrawRectangle {

    private Square square;

    public DrawSquare(Point topLeft, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, double size, boolean isBeveled, Layer layer) {
        super(topLeft, new Point(topLeft.getX()+ size, topLeft.getY() + size), primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        figure = new Square(topLeft, size);
        square = (Square)getFigure();

    }

    @Override
    public void draw(Figure selectedFigure, Color lineColor){
        drawShadow();

        drawBeveled();

        setGradientLinear();

        getGc().fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                square.width(), square.height());

        if(selected(selectedFigure)) {
            gc.setStroke(Color.RED);
        } else {
            gc.setStroke(lineColor);
        }

        getGc().strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                square.width(), square.height());
    }

    @Override
    public void turnRight(){
        square.turnRight();
    }

    @Override
    public void turnHorizontal(){
        square.turnHorizontal();
    }

    @Override
    public void turnVertical(){
        square.turnVertical();
    }

    @Override
    public DrawRectangle divide(){
        Rectangle temp = square.divideFigure();
        return new DrawRectangle(temp.getTopLeft(), temp.getBottomRight(), getFillColor(), getGradientColor(), gc, getShadowType(), isBeveled(), getLayer());
    }

    @Override
    public DrawRectangle duplicate(double offset){
        Point duplicatedTopLeft = new Point(square.getTopLeft().getX(), square.getTopLeft().getY());
        Point duplicatedBottomRight = new Point(square.getBottomRight().getX(), square.getBottomRight().getY());
        duplicatedTopLeft.move(offset, offset);
        duplicatedBottomRight.move(offset, offset);
        return new DrawRectangle(duplicatedTopLeft, duplicatedBottomRight, getFillColor(), getGradientColor(), gc, getShadowType(), isBeveled(), getLayer());
    }

}
