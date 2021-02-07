package com.example.blackjack;

public class ScoreModel {

    private String name;
    private int score;
    private int order;


    private ScoreModel() {}

    private ScoreModel(String name, int score) {
        this.score = score;
        this.name = name;
        this.order = score*(-1);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
