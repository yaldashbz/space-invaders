package model;
import javafx.scene.image.Image;

public abstract class Sprite {
    protected Image image;
    protected int size;
    protected boolean visible;
    protected int x;
    protected int y;
    protected int score;
    protected int direction;

    public Sprite() {
        visible = true;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public abstract void update();
}
