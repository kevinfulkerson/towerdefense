package com.goonsquad.galactictd.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;

public class ScoreScreen implements Screen, InputProcessor {
    private static final String TAG = "ScoreScreen";

    private GalacticTDGame gameInstance;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite greenShip;
    private Sprite redShip;
    private String scoresAsString;
    private String[] stringArray;
    private BitmapFont text;
    private boolean loaded;
    private float shipSpeedPerSecond;

    public ScoreScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
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
        greenShip.setPosition(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT - shipSize.y);
        greenShip.rotate(90);

        redShip = new Sprite(gameInstance.getAssetManager().get("tower-red.png", Texture.class));
        redShip.setSize(shipSize.x, shipSize.y);
        redShip.setOriginCenter();
        redShip.setPosition(0 - shipSize.x, 0);
        redShip.rotate(-90);

        loaded = true;
    }

    @Override
    public void render(float delta) {
        greenShip.translateX(shipSpeedPerSecond * delta * -1);
        redShip.translateX(shipSpeedPerSecond * delta);

        if (greenShip.getX() < (0 - greenShip.getWidth() * 2)) {
            greenShip.setX(GalacticTDGame.UI_WIDTH);
        }

        if (redShip.getX() > GalacticTDGame.UI_WIDTH + redShip.getWidth()) {
            redShip.setX(0 - (redShip.getWidth()));
        }
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();

        background.draw(batch);
        greenShip.draw(batch);
        redShip.draw(batch);
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
        Gdx.app.log(TAG, "show() called.");
        Gdx.input.setInputProcessor(this);
        if (!loaded) loadScreenObjects();

//        text = new BitmapFont();
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

