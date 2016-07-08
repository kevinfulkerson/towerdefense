package com.goonsquad.galactictd;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.goonsquad.galactictd.managers.Assets;
import com.goonsquad.galactictd.managers.PixmapUtils;
import com.goonsquad.galactictd.managers.ScoreManager;
import com.goonsquad.galactictd.managers.ScreenManager;
import com.goonsquad.galactictd.screens.LoadingScreen;

public class GalacticTDGame extends Game implements ApplicationListener {
    private static final String tag = "GalacticTDGame";
    public static final int UI_WIDTH = 1920;
    public static final int UI_HEIGHT = 1080;

    private ScreenManager screenManager;
    private ScoreManager scoreManager;
    private OrthographicCamera uiCamera;
    private FitViewport uiViewport;
    private SpriteBatch batch;
    private Texture background;
    public Assets assets;

    @Override
    public void create() {
        Gdx.app.log(tag, "create() called.");
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();
        background = PixmapUtils.generateRandomRepeatedTintedTexture(
                Gdx.files.internal("star.png"),
                UI_WIDTH, UI_HEIGHT,
                Color.WHITE, new Color(0xffb3b3ff), new Color(0x99c2ffff), new Color(0xffffccff));

        assets = new Assets();
        assets.loadAssets();

        scoreManager = new ScoreManager(5);

        screenManager = new ScreenManager(this);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(UI_WIDTH, UI_HEIGHT, uiCamera);
        uiViewport.apply(true);

        screenManager.setScreen(LoadingScreen.class);

        //LOG_DEBUG for trace+debug+error
        //LOG_INFO for debug+error
        //LOG_ERROR for error
        //LOG_NONE for none
        Gdx.app.setLogLevel(Application.LOG_INFO);
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public FitViewport getUiViewport() {
        return uiViewport;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(uiCamera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void resume() {
        assets.manager.finishLoading();
        super.resume();
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        Gdx.app.log(tag, "dispose() called.");
        background.dispose();
        scoreManager.dispose();
        screenManager.dispose();
        assets.dispose();
        super.dispose();
    }
}
