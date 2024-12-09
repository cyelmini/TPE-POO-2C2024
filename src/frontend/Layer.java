package frontend;

import java.util.Objects;

public class Layer {
    private final String name;
    private final int number;

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

    @Override
    public boolean equals(Object o){
         return o instanceof Layer layer &&
                 layer.name.equals(name) &&
                 layer.number == number;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, number);
    }
}
