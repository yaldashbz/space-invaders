package model;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public interface Commons {
    double SCENE_WIDTH = 700;
    double SCENE_HEIGHT = 500;
    String FONT = "Britannic Bold";
    String imagesPath = System.getProperty("user.dir") + File.separator + "images";
    String audiosPath = System.getProperty("user.dir") + File.separator + "audios";
    BackgroundImage backgroundImage = createBackgroundImage("background");
    BackgroundImage button = createBackgroundImage("button");
    BackgroundImage settings = createBackgroundImage("setting");
    BackgroundImage play = createBackgroundImage("play");
    BackgroundImage tick = createBackgroundImage("tick");
    BackgroundImage process = createBackgroundImage("process");
    BackgroundImage replay = createBackgroundImage("replay");
    Image rocket = createImage("rocket");

    static BackgroundImage createBackgroundImage(String name) {
        Image image = createImage(name);
        return new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    static Image createImage(String name) {
        FileInputStream input = null;
        try {
            input = new FileInputStream(imagesPath + File.separator + name + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert input != null;
        return new Image(input);
    }
}
