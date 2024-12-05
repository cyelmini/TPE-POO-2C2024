package frontend;

public class Layer {
    private String name;

    private int number;

    private boolean visible = true;
     public Layer (String name, int number){
        this.name = name;
        this.number = number;
     }

    public boolean isVisible(){
         return visible;
    }

    public void setVisible(boolean value){
        visible = value;
    }

    @Override
    public String toString(){
        return  name;
    }

    public int getNumber() {
         return number;
    }
}
