package model;

public class User {
    private String username;
    private String password;
    private int totalScore;
    private int wins;
    private int loses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalScore = 0;
        this.wins = 0;
        this.loses = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void increaseWins(int wins) {
        this.wins += wins;
    }

    public void increaseLoses(int loses) {
        this.loses += loses;
    }

    public void increaseScore(int score) {
        totalScore += score;
    }
}
