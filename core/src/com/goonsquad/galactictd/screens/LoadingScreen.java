package com.goonsquad.galactictd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.GalacticTDGame;

public class LoadingScreen implements Screen {
    private static final String TAG = "LoadingScreen";
    private GalacticTDGame gameInstance;

    private Sprite loadingScreenSprite;
    private SpriteBatch batch;

    public LoadingScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
        loadingScreenSprite = new Sprite(
                new Texture(Gdx.files.internal("loading.jpg")));
        loadingScreenSprite.setSize(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();
        loadingScreenSprite.draw(batch);
        batch.end();

        if (gameInstance.getAssetManager().update()) {
            gameInstance.getScreenManager().setScreen(HomeScreen.class);
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize() called.");
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
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
        Gdx.app.log(TAG, "pause() called.");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose() called.");
        loadingScreenSprite.getTexture().dispose();
    }
}
