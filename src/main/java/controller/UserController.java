package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;

public class UserController {
    private static UserController userController = null;
    private static final String path = System.getProperty("user.dir")  + File.separator + "players.json";
    private final ArrayList<User> users = new ArrayList<>();
    private User currentUser;

    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null)
            userController = new UserController();
        return userController;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getSortedPlayers() {
         users.sort(new ScoreSort());
         return users;
    }

    public void addToPlayers(User user) {
        users.add(user);
        write(users, path);
    }

    public void increaseScore(User user, int score) {
        user.increaseScore(score);
        write(users, path);
    }

    public void increaseWins(User user, int num) {
        user.increaseWins(num);
        write(users, path);
    }

    public void increaseLoses(User user, int num) {
        user.increaseLoses(num);
        write(users, path);
    }

    public boolean checkUsernameExists(String username) {
        return users.stream().anyMatch(player -> player.getUsername().equals(username));
    }

    public boolean checkCorrectPassword(String username, String password) {
        return getPlayerByUsername(username).getPassword().equals(password);
    }

    public void createAccount(String username, String password) {
        User user = new User(username, password);
        addToPlayers(user);
    }

    public void editPlayer(User user, String username, String password) {
        if (username.length() != 0)
            user.setUsername(username);
        if (password.length() != 0)
            user.setPassword(password);
        write(users, path);
    }

    public User getPlayerByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public ArrayList<User> handleJsonArray(String address) {
        ArrayList<User> patterns = new ArrayList<>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            BufferedReader br = new BufferedReader(new FileReader(address));
            JsonElement jsonElement = jsonParser.parse(br);
            Type arrayListType = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<User> temp = gson.fromJson(jsonElement, arrayListType);
            if (temp != null)
                return temp;
            return new ArrayList <> (  );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return patterns;
    }

    public static <T> void write(T obj, String address) {
        try {
            GsonBuilder builder = new GsonBuilder().enableComplexMapKeySerialization();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter(address);
            writer.write(gson.toJson(obj));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void initializePlayers() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        users.addAll(handleJsonArray(path));
    }
}

class ScoreSort implements Comparator<User> {

    @Override
    public int compare(User b, User a) {

        if (a.getTotalScore() - b.getTotalScore() != 0)
            return a.getTotalScore() - b.getTotalScore();
        else {
            if (a.getWins() - b.getWins() != 0)
                return a.getWins() - b.getWins();

            else {
                if (a.getLoses() - b.getLoses() != 0) {
                    return b.getLoses() - a.getLoses();
                }
                else
                    return b.getUsername().compareTo(a.getUsername());
            }
        }
    }
}
