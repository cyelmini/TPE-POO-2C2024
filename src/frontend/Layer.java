package frontend;

public class Layer {
    private String name;

    private int number;

    private boolean visible = true;
     public Layer (String name, int number, boolean visible){
        this.name = name;
        this.number = number;
        this.visible = visible;
    }

    public boolean isVisible(){
         return visible;
    }

    public void changeVisibility(){
        visible = !visible;
    }

    @Override
    public String toString(){
        return  name;
    }

}
