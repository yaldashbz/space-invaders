package model;

import controller.Game;

public class Bomb extends Sprite {
    private final int speed;

    public Bomb() {
        this.image = Commons.createImage("bomb");
        this.visible = false;
        this.size = 20;
        this.speed = 7;
    }

    @Override
    public void update() {
        if (visible)
            y += speed;
        if (y > 390)
            visible = false;
    }

    public void collide(Sprite other) {
        if (this.isVisible() && other.isVisible() && this.y + this.size >= other.y && this.y + this.size <= other.y + other.size
        && this.x + this.size >= other.x && this.x <= other.x + other.size) {
            visible = false;
            if (other instanceof Shot) {
                other.visible = false;
                Game.getGame().setShot(new Shot());
            }
            Game.getGame().setExplodeX(x);
            Game.getGame().setExplodeY(y);
            Game.getGame().setExplode(true);
            if (other instanceof Player) {
                Game.getGame().reduceLives();
            }
        }
    }
}
