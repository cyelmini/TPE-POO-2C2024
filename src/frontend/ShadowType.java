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
    COLOUR(10.0){

        @Override
        public String toString(){
            return "Colour";
        }
    },
    SIMPLE_INVERSE(-10.0){

        @Override
        public String toString(){
            return "Simple inverse";
        }
    },
    COLOUR_INVERSE(-10.0){

        @Override
        public String toString(){
            return "Colour inverse";
        }
    };

    private final double offset;

    ShadowType(double offset){
        this.offset = offset;
    }

}
