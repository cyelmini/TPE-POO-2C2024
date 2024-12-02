package frontend;

public enum ShadowType {

    NO_SHADOW(0){

        @Override
        public String toString(){
            return "No shadow";
        }
    },
    SIMPLE(10.0){

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
        public String toString(){
            return "Simple inverse";
        }
    },
    COLOR_INVERSE(-10.0){

        @Override
        public String toString(){
            return "Color inverse";
        }
    };

    private final double offset;

    ShadowType(double offset){
        this.offset = offset;
    }

}
