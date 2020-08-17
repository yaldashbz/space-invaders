package model;

import controller.Game;

public class Shot extends Sprite {
    private final int speed = 10;
    public Shot() {

    }

    public Shot(int x, int y) {
        this.image = Commons.createImage("shot");
        this.x = x + 10;
        this.y = y - 1;
        this.size = 15;
    }

    @Override
    public void update() {
        y -= speed;
        if (y < 0)
            this.visible = false;
    }

    public void collide(Sprite other) {
        boolean dx;
        dx = this.x + this.size >= other.x && this.x + this.size <= other.x + other.size ;

        if (other.isVisible() && dx && this.y + this.size >= other.y && this.y  <= other.y + other.size ) {
            visible = false;
            other.visible = false;
            Game.getGame().setExplodeX(x);
            Game.getGame().setExplodeY(y);
            Game.getGame().setExplode(true);
            if (other instanceof Alien)
                Game.getGame().increaseScore(other.getScore());
            if (other instanceof UFO)
                Game.getGame().increaseScore(Game.getGame().getScore() / 10);
            Game.getGame().setShot(new Shot());
        }
    }
}
