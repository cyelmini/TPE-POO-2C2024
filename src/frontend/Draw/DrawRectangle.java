package frontend.Draw;

import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.ShadowType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawRectangle extends DrawFigure {

    private final Rectangle rectangle;

    public DrawRectangle(Point topLeft, Point bottomRight, Color primaryColor, Color secondaryColor, GraphicsContext gc, ShadowType shadowType, boolean isBeveled){
        super(primaryColor, secondaryColor, gc, shadowType, isBeveled);
        figure = new Rectangle(topLeft, bottomRight);
        rectangle = (Rectangle) getFigure();

    }

    @Override
    public void drawShadow(){
        Color shadowColor = getShadowColor();
        if (shadowColor != Color.TRANSPARENT) {
            getGc().setFill(shadowColor);
            double offset = getOffset();
            getGc().fillRect(rectangle.getTopLeft().getX() + offset, rectangle.getTopLeft().getY() + offset, rectangle.width(), rectangle.height());
        }
    }

    @Override
    public void drawBeveled(){
        if(isBeveled()){
            double x = rectangle.getTopLeft().getX();
            double y = rectangle.getTopLeft().getY();
            double width = Math.abs(x - rectangle.getBottomRight().getX());
            double height = Math.abs(y - rectangle.getBottomRight().getY());

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
    public void draw(Figure selectedFigure, Color lineColor){
        drawShadow();

        drawBeveled();

        setGradientLinear();

        getGc().fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());

        if(selected(selectedFigure)) {
            gc.setStroke(Color.RED);
        } else {
            gc.setStroke(lineColor);
        }

        getGc().strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(),
                rectangle.width(), rectangle.height());
    }

}
