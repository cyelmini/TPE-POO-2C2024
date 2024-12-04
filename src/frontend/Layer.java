package frontend;

public class Layer {
    private final int layer;
    private final String name;
    private boolean visible = true;

    public Layer(int layer, String name) {
        this.layer = layer;
        this.name = name;
    }

    public int getLayer() {
        return layer;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void changeVisibility() {
        visible = !visible;
    }
}
