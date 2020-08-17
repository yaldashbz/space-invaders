package model;

import controller.Game;

public class Alien extends Sprite {
    private Bomb bomb;
    private int speedX = 6;
    private int speedY = 10;
    public enum AlienType {
        ROBOT, ALIEN, SPACESHIP
    }

    public Alien(int x, int y, AlienType type) {
        this.x = x;
        this.y = y;
        this.image = type.equals(AlienType.ROBOT) ? Commons.createImage("robot") :
                type.equals(AlienType.SPACESHIP) ? Commons.createImage("alien2") : Commons.createImage("alien");
        this.direction = -1;
        bomb = new Bomb();
        this.size = 40;
        this.score = type.equals(AlienType.ROBOT) ? 10 : type.equals(AlienType.SPACESHIP) ? 20 : 30;
    }

    @Override
    public void update() {
        x += speedX * direction;
        if (x < 0 || x > 650) {
            x = direction > 0 ? 650 : 0;
            y += speedY;
            direction *= -1;
        }
        if (y > 410) {
            Game.getGame().setGameOver(true);
            Game.getGame().setGotYou(true);
            visible = false;
        }
    }

    public void shoot() {
        bomb.x = x;
        bomb.y = y;
    }

    public Bomb getBomb() {
        return bomb;
    }

}
