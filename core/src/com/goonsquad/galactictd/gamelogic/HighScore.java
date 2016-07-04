package com.goonsquad.galactictd.gamelogic;

public class HighScore {
    private int score;

    public HighScore() {
        score = 0;
    }

    public HighScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
