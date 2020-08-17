package model;

import controller.Game;

public class UFO extends Sprite {
    private final int speed;
    public UFO() {
        this.x = 650;
        this.y = 50;
        this.image = Commons.createImage("ufo");
        this.score = Game.getGame().getScore() / 10;
        this.visible = false;
        this.size = 65;
        this.direction = -1;
        this.speed = 8;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void update() {
        if (isVisible())
            x -= speed;
        if (x < 0) {
            this.visible = false;
            x = 700;
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (!visible)
            x = 700;
    }
}
