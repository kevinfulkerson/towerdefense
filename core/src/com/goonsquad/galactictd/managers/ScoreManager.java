package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import com.goonsquad.galactictd.gamelogic.HighScore;

import java.io.IOException;

public class ScoreManager implements Disposable {

    private final HighScore[] highScores = new HighScore[5];
    private Json jsonUtil;

    public ScoreManager() {
        jsonUtil = new Json();
        setDefaultScores();

        try {
            readScores();
        } catch (IOException e) {

        }
    }

    private void readScores() throws IOException {

    }

    private void writeScores() throws IOException {
        FileHandle saveFile = Gdx.files.local("save.json");
        saveFile.writeString("", false);
        for (HighScore score : highScores) {
            String output = jsonUtil.toJson(score, HighScore.class);
            saveFile.writeString(output, true);
        }
    }

    private void setDefaultScores() {
        int defaultScore = 100;
        for (int i = 0; i < highScores.length; i++) {
            highScores[i] = new HighScore(defaultScore);
            defaultScore += 100;
        }
    }

    public void addScore(HighScore newScore) {
        for (int i = highScores.length - 1; i >= 0; i--) {
            if (highScores[i].getScore() < newScore.getScore()) {
                HighScore oldScore = new HighScore(highScores[i].getScore());
                highScores[i] = newScore;
                newScore = oldScore;
            }
        }
    }

    @Override
    public void dispose() {
        try {
            writeScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
