package frontend;

import backend.model.Figure;
import javafx.scene.paint.Color;

public abstract class DrawFigure {

    private Figure figure;
    private Color color;

    public abstract void draw();

}
