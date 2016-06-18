package com.goonsquad.galactictd.managers;

import java.io.IOException;

public class ScoreManager {

    private final int[] highScores = new int[5];

    public ScoreManager() {
        setDefaultScores();
        try {
            readScores();
        } catch (IOException e) {

        }
    }

    private void readScores() throws IOException {

    }

    private void setDefaultScores() {
        int defaultScore = 100;
        for (int i = 0; i < highScores.length; i++) {
            highScores[i] = defaultScore;
            defaultScore += 100;
        }
    }

    public void addScore(int newScore) {
    }
}
