package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.goonsquad.galactictd.gamelogic.HighScore;

import java.util.ArrayList;
import java.util.Arrays;

public class ScoreManager implements Disposable {
    private static final String TAG = "ScoreManager";
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
            if (text == null || text.equals("")) throw new GdxRuntimeException("Save file empty.");
            try {
                ArrayList<HighScore> savedScores = jsonUtil.fromJson(ArrayList.class, HighScore.class, text);
                scoreHolder.addArrayListOfScores(savedScores);
            } catch (SerializationException se) {
                logErrorReadingSaveFile();
            }
        } catch (GdxRuntimeException e) {
            logErrorReadingSaveFile();
        }
    }

    private void logErrorReadingSaveFile() {
        Gdx.app.log(TAG, "Error reading save file.");
    }

    private void writeScores() {
        saveFile.writeString(jsonUtil.toJson(scoreHolder.getScoresAsArrayList()), false);
    }

    public void addScore(HighScore incomingScore) {
        scoreHolder.addScore(incomingScore);
    }

    public ArrayList<HighScore> getScores() {
        return scoreHolder.getScoresAsArrayList();
    }

    @Override
    public void dispose() {
        writeScores();
    }

    private class ScoreHolder {
        private HighScore[] highScores;

        protected ScoreHolder(int maxSize) {
            initScores(maxSize);
        }

        private void initScores(int maxSize) {
            highScores = new HighScore[maxSize];
            Arrays.fill(highScores, new HighScore());
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
            returnList.addAll(Arrays.asList(highScores));
            return returnList;
        }
    }
}
