package com.goonsquad.galactictd.screens;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.goonsquad.galactictd.managers.ScreenManager;

public class ScoreScreen implements Screen, InputProcessor {

    private final String tag = "ScoreScreen";
    private SpriteBatch batch;
    private Sprite background, sprite1, sprite2;
    private FileHandle scoresDataFile;
    private String scoresAsString;
    private ArrayList<Integer> highScores = new ArrayList<Integer>();
    private String[] stringArray;
    private BitmapFont text;

    public ScoreScreen() {
        Gdx.app.log(tag, "Initialized " + tag);
        batch = new SpriteBatch();
        createScoreScreenSprites();
    }

    private void createScoreScreenSprites() {
        background = new Sprite(new Texture(Gdx.files.internal("enemy1.png")));
//
//        sprite1 = new Sprite(GalacticTDGame.instance().getAssetManager().get("tower-green.png", Texture.class));
//        sprite1.rotate90(true);
//        sprite1.rotate90(true);
//        sprite1.rotate90(true);
//        sprite1.setBounds(width, (height - (width / 6)), width / 10, width / 10);
//
//
//        sprite2 = new Sprite(GalacticTDGame.instance().getAssetManager().get("tower-red.png", Texture.class));
//        sprite2.rotate90(true);
//        sprite2.setBounds(-width / 10, height / 6, width / 10, width / 10);
    }

    @Override
    public void render(float delta) {
//        sprite1.translateX(-9);
//        sprite2.translateX(7);
//
//        if (sprite1.getX() < -width / 10) {
//            sprite1.setPosition(width, (height - (width / 6)));
//        }
//        if (sprite2.getX() > width) {
//            sprite2.setPosition(-width / 10, height / 6);
//        }

        batch.begin();

        background.draw(batch);
//        sprite1.draw(batch);
//        sprite2.draw(batch);
//
//        text.draw(batch, "HIGH SCORES:", (width / 10), (height / 16) * 15);
//        text.draw(batch, "" + highScores.get(0), (width / 10) * 6, (height / 16) * 15);
//        text.draw(batch, "" + highScores.get(1), (width / 10) * 6, (height / 16) * 13);
//        text.draw(batch, "" + highScores.get(2), (width / 10) * 6, (height / 16) * 11);
//        text.draw(batch, "" + highScores.get(3), (width / 10) * 6, (height / 16) * 9);
//        text.draw(batch, "" + highScores.get(4), (width / 10) * 6, (height / 16) * 7);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.app.log(this.tag, "show() called.");
        Gdx.input.setInputProcessor(this);

//        getScoresFile();


//        text = new BitmapFont();
    }

    public void sendScore(int score) {
        getScoresFile();
        sortScores();
        int newScore = newHighScore(score);
        if (newScore != 5) {
            highScores.add(newScore, score);
            highScores.remove(5);
        }
        scoresDataFile.writeString(highScores.get(0) + " " + highScores.get(1) + " " + highScores.get(2) + " "
                + highScores.get(3) + " " + highScores.get(4), false);
    }

    private int newHighScore(int score) {
        for (int i : highScores) {
            if (score > i) {
                return highScores.indexOf(i);
            }
        }
        return 5;
    }

    public void getScoresFile() {
        try {
            scoresDataFile = Gdx.files.local("highScores.txt");
            scoresAsString = scoresDataFile.readString();
        } catch (GdxRuntimeException e) {
            scoresDataFile = Gdx.files.local("highScores.txt");
            scoresDataFile.writeString("0 0 0 0 0", false);
            scoresDataFile = Gdx.files.local("highScores.txt");
            scoresAsString = scoresDataFile.readString();
        }
        stringArray = scoresAsString.split(" ");
        for (int i = 0; i < 5; i++) {
            highScores.add(Integer.parseInt(stringArray[i]));
        }
        sortScores();
    }

    public void sortScores() {
        int[] temp = new int[5];
        for (int i = 0; i < 5; i++) {
            temp[i] = highScores.remove(0);
        }

        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 5; k++) {
                if (temp[j] > temp[k]) {
                    int holder = temp[j];
                    temp[j] = temp[k];
                    temp[k] = holder;
                }
            }
        }

        for (int s : temp) {
            highScores.add(s);
        }
    }

    @Override
    public void resize(int width, int height) {
        background.setBounds(0, 0, width, height);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
//        scoresDataFile = null;
    }

    @Override
    public void dispose() {
        Gdx.app.log(tag, "dispose() called.");
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        ScreenManager.instance().setScreen(HomeScreen.class);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

