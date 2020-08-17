package controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.*;
import view.GameMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Commons {
    private static Game game = null;
    private final GameMenu gameMenu;
    private static final Random RAND = new Random();
    private final ArrayList<Star> universe;
    private final Player player;
    private Shot shot;
    private final Image explosion;
    private final GraphicsContext gc;
    private ArrayList<Alien> aliens;
    private final UFO ufo;
    private String message;
    private int lives = 3;
    private int score = 0;
    private boolean gameOver;
    private boolean won;
    private boolean gotYou;
    private boolean explode;
    private int explodeX;
    private int explodeY;


    public Game(GraphicsContext gc, GameMenu gameMenu) {
        game = this;
        createAliens();
        explosion = Commons.createImage("explosion");
        this.gc = gc;
        this.player = new Player();
        this.universe = new ArrayList<>();
        this.ufo = new UFO();
        this.shot = new Shot();
        this.gameMenu = gameMenu;
    }

    private void createAliens() {
        int alienX = 150;
        int alienY = 25;
        aliens = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Alien alien = new Alien(alienX + 50 * i, alienY, Alien.AlienType.ALIEN);
            aliens.add(alien);
        }

        for (int i = 0; i < 6; i++) {
            Alien alien = new Alien(alienX + 50 * i, alienY + 50, Alien.AlienType.SPACESHIP);
            aliens.add(alien);
        }

        for (int i = 0; i < 6; i++) {
            Alien alien = new Alien(alienX + 50 * i, alienY + 100 , Alien.AlienType.ROBOT);
            aliens.add(alien);
        }
    }

    public void run() {
        if (gameOver || won) {
            message = won ?"Congratulation! You won." : gotYou ? "Game Over! They got you" : "Game over! It was a bad explosion";
            gameMenu.endGame();
        } else {
            gc.setFill(Color.grayRgb(20));
            gc.fillRect(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
            drawLine();
            drawScore();
            drawStars();

            drawPlayer();
            if (lives == 3) drawLives();
            if (lives == 2) drawLive();

            aliens.forEach(Alien::update);
            drawAliens();

            shot.update();
            drawShooting();

            shot.collide(ufo);
            aliens.forEach(alien -> shot.collide(alien));

            aliens.forEach(alien -> {
                if (alien.isVisible()) {
                    alien.getBomb().update();
                    drawBomb(alien.getBomb());
                    alien.getBomb().collide(shot);
                    alien.getBomb().collide(player);
                }
            });

            updateBombing();

            drawExplosion();

            if (score % 30 == 0 && score != 0) {
                ufo.setVisible(true);
            }

            drawUFO();
            ufo.update();

            won = aliens.stream().noneMatch(Alien::isVisible);
            player.update();

            if (RAND.nextInt(10) > 2) {
                universe.add(new Star());
            }

            ArrayList<Star> garbage = new ArrayList<>();
            for (Star star : universe) {
                if (star.posY > SCENE_HEIGHT)
                    garbage.add(star);
            }
            universe.removeAll(garbage);
        }
    }

    public void updateBombing() {
        int a = RAND.nextInt(18);
        int b = RAND.nextInt(18);
        for (int i = Math.max(Math.max(a, b) - 3, 0); i < Math.max(a, b); i++) {
            if (!aliens.get(i).getBomb().isVisible() && aliens.get(i).isVisible()) {
                aliens.get(i).getBomb().setVisible(true);
                aliens.get(i).shoot();
            }
        }
    }

    public void drawBomb(Bomb bomb) {
        if (bomb.isVisible())
            gc.drawImage(bomb.getImage(), bomb.getX(), bomb.getY());
    }

    public int getScore() {
        return score;
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);

        int x = player.getX();
        int y = player.getY();

        if (!gameOver) {
            KeyCode code = e.getCode();
            if (code == KeyCode.ENTER) {
                if (!shot.isVisible()) {
                    shot = new Shot(x, y);
                    shot.setVisible(true);
                }
            }
        }
    }

    public void playExplosionSound() {
        File file = new File(audiosPath + File.separator + "explosion.mp3");
        String path = file.toURI().toString();
        AudioClip sound = new AudioClip(path);
        sound.play(0.7);
    }

    private void drawExplosion() {
        if (explode) {
            gc.drawImage(explosion, explodeX, explodeY);
            playExplosionSound();
        }
        explode = false;
    }

    private void drawScore() {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font(20));
        gc.setFill(Color.WHITE);
        gc.fillText("Score : " + score, 60,  30);
    }

    public void drawShooting() {
        if (shot.isVisible())
            gc.drawImage(shot.getImage(), shot.getX(), shot.getY());
    }

    private void drawUFO() {
        if (ufo.isVisible())
            gc.drawImage(ufo.getImage(), ufo.getX(), ufo.getY());
    }

    private void drawStars() {
        universe.forEach(Star::draw);
    }

    public void drawPlayer() {
        if (player.isVisible())
            gc.drawImage(player.getImage(), player.getX(), player.getY());
    }

    public void drawLives() {
        for (int i = 0; i < 2; i++) {
            gc.drawImage(player.getPlayerLive(), 60 * i + 10, 430);
        }
    }

    public void drawLive() {
        gc.drawImage(player.getPlayerLive(), 10, 430);
    }

    public void drawLine() {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(0, 410, SCENE_WIDTH, 410);
    }

    public void drawAliens() {
        for (Alien alien : aliens) {
            if (alien.isVisible())
                gc.drawImage(alien.getImage(), alien.getX(), alien.getY());
        }
    }

    public static Game getGame() {
        return game;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void reduceLives() {
        lives --;
    }

    public int getLives() {
        return lives;
    }

    public String getMessage() {
        return message;
    }

    public void setExplodeX(int explodeX) {
        this.explodeX = explodeX;
    }

    public void setExplodeY(int explodeY) {
        this.explodeY = explodeY;
    }

    public void setExplode(boolean explode) {
        this.explode = explode;
    }

    public void setGotYou(boolean gotYou) {
        this.gotYou = gotYou;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public boolean getWon() {
        return won;
    }

    public class Star {

        int posX, posY;
        private final int h, w, r, g, b;
        private double opacity;

        public Star() {
            posX = RAND.nextInt((int) SCENE_WIDTH);
            posY = 0;
            w = RAND.nextInt(5) + 1;
            h =  RAND.nextInt(5) + 1;
            r = RAND.nextInt(100) + 150;
            g = RAND.nextInt(100) + 150;
            b = RAND.nextInt(100) + 150;
            opacity = RAND.nextFloat();
            if(opacity < 0) opacity *= -1;
            if(opacity > 0.5) opacity = 0.5;
        }

        public void draw() {
            if(opacity > 0.8) opacity -= 0.01;
            if(opacity < 0.1) opacity += 0.01;
            gc.setFill(Color.rgb(r, g, b, opacity));
            gc.fillOval(posX, posY, w, h);
            posY += 5;
        }
    }
}


