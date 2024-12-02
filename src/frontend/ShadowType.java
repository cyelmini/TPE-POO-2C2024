package frontend;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum ShadowType {

    NO_SHADOW(0){
        @Override
        public Color getColor(Color color){
            return Color.TRANSPARENT;
        }

        @Override
        public String toString(){
            return "Sin sombra";
        }
    },
    SIMPLE(10.0){
        @Override
        public Color getColor(Color color){
            return Color.GREY;
        }

        @Override
        public String toString(){
            return "Simple";
        }
    },
    COLOR(10.0){
        @Override
        public String toString(){
            return "Color";
        }
    },
    SIMPLE_INVERSE(-10.0){

        @Override
        public Color getColor(Color color){
            return Color.GREY;
        }

        @Override
        public String toString(){
            return "Simple inverso";
        }
    },
    COLOR_INVERSE(-10.0){
        @Override
        public String toString(){
            return "Color inverso";
        }
    };

    private final double offset;

    ShadowType(double offset){
        this.offset = offset;
    }

    public Color getColor(Color color){
        return color.darker();
    }

    public double getOffset() {
        return offset;
    }

}
