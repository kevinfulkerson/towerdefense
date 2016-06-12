package com.goonsquad.galactictd.screens;
//
//import interfaces.IScreenInput;
//import gamelogic.ScreenState;
//import towerdefense.TowerDefense;

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
    private InputProcessor inputProcessor;

    public LoadingScreen() {
        batch = new SpriteBatch();
        loadingScreenSprite = new Sprite(new Texture(Gdx.files.internal("loading.jpg")));
    }

    @Override
    public void render(float delta) {
        if (GalacticTDGame.instance().getAssetManager().update()) {
            ScreenManager.instance().setScreen(LoadingScreen.class);
        } else {
            batch.begin();
            loadingScreenSprite.draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        loadingScreenSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(inputProcessor);
        Gdx.app.log(this.tag, "Show was called.");
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        loadingScreenSprite.getTexture().dispose();
    }
}
