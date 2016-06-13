package com.goonsquad.galactictd.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.managers.ScreenManager;

public class LoadingScreen implements Screen {

    private final String tag = "LoadingScreen";
    private Sprite loadingScreenSprite;
    private SpriteBatch batch;

    public LoadingScreen() {
        batch = new SpriteBatch();
        loadingScreenSprite = new Sprite(new Texture(Gdx.files.internal("loading.jpg")));
    }

    @Override
    public void render(float delta) {
        if (GalacticTDGame.instance().getAssetManager().update()) {
            ScreenManager.instance().setScreen(HomeScreen.class);
        } else {
            batch.begin();
            loadingScreenSprite.draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(tag, "resize() called.");
    }

    @Override
    public void show() {
        loadingScreenSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.app.log(this.tag, "show() called.");
    }

    @Override
    public void hide() {
        Gdx.app.log(tag, "hide() called.");
    }

    @Override
    public void pause() {
        Gdx.app.log(tag, "pause() called.");
    }

    @Override
    public void resume() {
        Gdx.app.log(tag, "pause() called.");
    }

    @Override
    public void dispose() {
        Gdx.app.log(tag, "dispose() called.");
        loadingScreenSprite.getTexture().dispose();
    }
}
