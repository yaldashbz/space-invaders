package model;

import controller.Game;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player extends Sprite {
    private Image playerLive;
    private int width;

    public Player() {
        this.x = 330;
        this.y = 350;
        this.size = 50;
        this.image = Commons.createImage("player");
        playerLive = Commons.createImage("playerLive");
        width = (int) this.image.getWidth();
    }

    @Override
    public void update() {
        if (Game.getGame().getLives() <= 0) {
            this.die();
            Game.getGame().setGameOver(true);
        }
    }

    public Image getPlayerLive() {
        return playerLive;
    }

    public void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.RIGHT && x + 6 <= 650)
            x += 6;
        if (code == KeyCode.LEFT && x - 6 >= 0)
            x -= 6;
    }
}

