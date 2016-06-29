package com.goonsquad.galactictd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.gamelogic.HighScore;

import java.util.ArrayList;

public class ScoreScreen implements Screen, InputProcessor {
    private static final String TAG = "ScoreScreen";

    private GalacticTDGame gameInstance;
    private Vector2 textPosition;
    private Vector2 greenShipStartPosition;
    private Vector2 redShipStartPosition;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite greenShip;
    private Sprite redShip;
    private ArrayList<HighScore> highScoreArrayList;
    private BitmapFont text;
    private boolean loaded;
    private float shipSpeedPerSecond;
    private int textHeightFactor;

    public ScoreScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
        generateFont();
        loaded = false;
        shipSpeedPerSecond = 400f;

    }

    private void loadScreenObjects() {
        background = new Sprite(gameInstance.getAssetManager().get("space2.jpg", Texture.class));
        background.setSize(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

        Vector2 shipSize = new Vector2(GalacticTDGame.UI_WIDTH / 16, GalacticTDGame.UI_WIDTH / 16f);

        greenShip = new Sprite(gameInstance.getAssetManager().get("tower-green.png", Texture.class));
        greenShip.setSize(shipSize.x, shipSize.y);
        greenShip.setOriginCenter();
        greenShipStartPosition = new Vector2(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT - shipSize.y);
        greenShip.setPosition(greenShipStartPosition.x, greenShipStartPosition.y);
        greenShip.rotate(90);

        redShip = new Sprite(gameInstance.getAssetManager().get("tower-red.png", Texture.class));
        redShip.setSize(shipSize.x, shipSize.y);
        redShip.setOriginCenter();
        redShipStartPosition = new Vector2(0 - shipSize.x, 0);
        redShip.setPosition(redShipStartPosition.x, redShipStartPosition.y);
        redShip.rotate(-90);

        textPosition = new Vector2(GalacticTDGame.UI_WIDTH / 5, GalacticTDGame.UI_HEIGHT / 20);
        loaded = true;
    }

    private void generateFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NixieOne.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.WHITE;
        parameter.borderWidth = 1.3f;
        text = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render(float delta) {
        greenShip.translateX(shipSpeedPerSecond * delta * -1);
        redShip.translateX(shipSpeedPerSecond * delta);

        if (greenShip.getX() < (0 - greenShip.getWidth() * 2)) {
            greenShip.setX(greenShipStartPosition.x);
        }

        if (redShip.getX() > GalacticTDGame.UI_WIDTH + redShip.getWidth()) {
            redShip.setX(redShipStartPosition.x);
        }
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();

//        background.draw(batch);
        greenShip.draw(batch);
        redShip.draw(batch);

        textHeightFactor = 15;
        text.draw(batch, "High Scores:", textPosition.x, textPosition.y * 15);
        for (HighScore highScore : highScoreArrayList) {
            text.draw(batch, "" + highScore.getScore(), textPosition.x * 3, textPosition.y * textHeightFactor);
            textHeightFactor -= 2;
        }
        batch.end();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
        Gdx.input.setInputProcessor(this);
        if (!loaded) loadScreenObjects();
        highScoreArrayList = gameInstance.getScoreManager().getScores();
    }


    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize() called. Width: " + width + " Height: " + height);
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide() called.");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause() called.");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume() called.");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose() called.");
        text.dispose();
        batch.dispose();
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
        gameInstance.getScreenManager().setScreen(HomeScreen.class);
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

