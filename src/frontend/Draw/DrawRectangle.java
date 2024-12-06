package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.Layer;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawRectangle(Point topLeft, Point bottomRight, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, boolean isBeveled, Layer layer){
        super(primaryColor, secondaryColor, gc, shadowType, isBeveled, layer);
        figure = new Rectangle(topLeft, bottomRight);
        rectangle = (Rectangle) getFigure();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Figure selectedFigure, Color lineColor){
        drawShadow();

        drawBeveled();

        setGradientLinear();

        getGc().fillRect(getRectangle().getTopLeft().getX(), getRectangle().getTopLeft().getY(),
                getRectangle().width(), getRectangle().height());

        if(selected(selectedFigure)) {
            gc.setStroke(Color.RED);
        } else {
            gc.setStroke(lineColor);
        }

        getGc().strokeRect(getRectangle().getTopLeft().getX(), getRectangle().getTopLeft().getY(),
                getRectangle().width(), getRectangle().height());
    }

    @Override
    public void drawShadow(){
        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            getGc().setFill(shadowColor);
            double offset = getOffset();
            getGc().fillRect(getRectangle().getTopLeft().getX() + offset, getRectangle().getTopLeft().getY() + offset, getRectangle().width(), getRectangle().height());
        }
    }

    @Override
    public void drawBeveled(){
        if(isBeveled()){
            double x = getRectangle().getTopLeft().getX();
            double y = getRectangle().getTopLeft().getY();
            double width = Math.abs(x - getRectangle().getBottomRight().getX());
            double height = Math.abs(y - getRectangle().getBottomRight().getY());

            getGc().setLineWidth(10);
            getGc().setStroke(Color.LIGHTGRAY);
            getGc().strokeLine(x, y, x + width, y);
            getGc().strokeLine(x, y, x, y + height);

            getGc().setStroke(Color.BLACK);
            getGc().strokeLine(x + width, y, x + width, y + height);
            getGc().strokeLine(x, y + height, x + width, y + height);
            gc.setLineWidth(1);
        }
    }

    @Override
    public void turnRight(){
        getRectangle().turnRight();
    }

    @Override
    public void turnHorizontal(){
        getRectangle().turnHorizontal();
    }

    @Override
    public void turnVertical(){
        getRectangle().turnVertical();
    }

    @Override
    public DrawRectangle divide(){
        Rectangle temp = getRectangle().divideFigure();
        return new DrawRectangle(temp.getTopLeft(), temp.getBottomRight(), getFillColor(), getGradientColor(), gc, getShadowType(), isBeveled(), getLayer());
    }

    @Override
    public DrawRectangle duplicate(double offset){
        Point duplicatedTopLeft = new Point(getRectangle().getTopLeft().getX(), getRectangle().getTopLeft().getY());
        Point duplicatedBottomRight = new Point(getRectangle().getBottomRight().getX(), getRectangle().getBottomRight().getY());
        duplicatedTopLeft.move(offset, offset);
        duplicatedBottomRight.move(offset, offset);
        return new DrawRectangle(duplicatedTopLeft, duplicatedBottomRight, getFillColor(), getGradientColor(), gc, getShadowType(), isBeveled(), getLayer());
    }

}
