package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.goonsquad.galactictd.gamelogic.HighScore;

import java.util.ArrayList;

public class ScoreManager implements Disposable {
    private Json jsonUtil;
    private FileHandle saveFile;
    private ScoreHolder scoreHolder;

    public ScoreManager(int maxAmountOfHighScores) {
        scoreHolder = new ScoreHolder(maxAmountOfHighScores);
        jsonUtil = new Json();
        saveFile = Gdx.files.local("save.json");
        readScores();
    }

    private void readScores() {
        try {
            String text = saveFile.readString();
            ArrayList<HighScore> savedScores = jsonUtil.fromJson(ArrayList.class, text);
            scoreHolder.addArrayListOfScores(savedScores);
        } catch (GdxRuntimeException e) {

        }
    }

    private void writeScores() {
        saveFile.writeString("", false);
        saveFile.writeString(jsonUtil.toJson(scoreHolder.getScoresAsArrayList()), false);
    }

    public void addScore(HighScore incomingScore) {
        scoreHolder.addScore(incomingScore);
    }

    @Override
    public void dispose() {
        writeScores();
    }

    private class ScoreHolder {
        private HighScore[] highScores;

        ScoreHolder(int maxSize) {
            initScores(maxSize);
        }

        private void initScores(int maxSize) {
            highScores = new HighScore[maxSize];
            for (int i = 0; i < maxSize; i++) {
                highScores[i] = new HighScore(0);
            }
        }

        public void addScore(HighScore incomingScore) {
            for (int i = 0; i < highScores.length; i++) {
                if (incomingScore.getScore() >= highScores[i].getScore()) {
                    HighScore oldHighScore = highScores[i];
                    highScores[i] = incomingScore;
                    incomingScore = oldHighScore;
                }
            }
        }

        public void addArrayListOfScores(ArrayList<HighScore> incomingScores) {
            if (incomingScores != null) {
                int added = 0;
                for (HighScore incomingScore : incomingScores) {
                    if (added > highScores.length - 1) break;
                    this.addScore(incomingScore);
                    added++;
                }
            }
        }

        public ArrayList<HighScore> getScoresAsArrayList() {
            ArrayList<HighScore> returnList = new ArrayList<HighScore>();
            for (HighScore hs : highScores) {
                returnList.add(hs);
            }
            return returnList;
        }
    }
}
