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
import com.goonsquad.galactictd.managers.PixmapUtils;
import com.goonsquad.galactictd.managers.ScoreManager;
import com.goonsquad.galactictd.managers.ScreenManager;
import com.goonsquad.galactictd.screens.LoadingScreen;

public class GalacticTDGame extends Game implements ApplicationListener {
    private static final String tag = "GalacticTDGame";
    public static final int UI_WIDTH = 1920;
    public static final int UI_HEIGHT = 1080;

    private AssetManager assetManager;
    private ScreenManager screenManager;
    private ScoreManager scoreManager;
    private OrthographicCamera uiCamera;
    private FitViewport uiViewport;
    private SpriteBatch batch;
    private Texture background;

    @Override
    public void create() {
        Gdx.app.log(tag, "create() called.");
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();
        background = PixmapUtils.generateRandomRepeatedTintedTexture(
                Gdx.files.internal("star.png"),
                UI_WIDTH, UI_HEIGHT,
                Color.WHITE, new Color(0xffb3b3ff), new Color(0x99c2ffff), new Color(0xffffccff));

        assetManager = new AssetManager();
        this.loadGameAssets();

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

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    //TODO
    //Create custom class for assets.
    private void loadGameAssets() {
        Gdx.app.log(tag, "Loading game assets.");
        assetManager.load("star.png", Texture.class);
        assetManager.load("music_icon.png", Texture.class);
        assetManager.load("black.png", Texture.class);
        assetManager.load("blankTextBorder.png", Texture.class);
        assetManager.load("border.png", Texture.class);
        assetManager.load("borderSelected.png", Texture.class);
        assetManager.load("buttonBuy.png", Texture.class);
        assetManager.load("buttonCancel.png", Texture.class);
        assetManager.load("buttonIntro.png", Texture.class);
        assetManager.load("buttonPlay.png", Texture.class);
        assetManager.load("buttonQuit.png", Texture.class);
        assetManager.load("buttonScore.png", Texture.class);
        assetManager.load("enemy1.png", Texture.class);
        assetManager.load("enemy2.png", Texture.class);
        assetManager.load("fireBall.png", Texture.class);
        assetManager.load("galacticTD.png", Texture.class);
        assetManager.load("loading.jpg", Texture.class);
        assetManager.load("mapDefault.png", Texture.class);
        assetManager.load("mapSelected.png", Texture.class);
        assetManager.load("pathLeft.png", Texture.class);
        assetManager.load("pathRight.png", Texture.class);
        assetManager.load("pathStraight.png", Texture.class);
        assetManager.load("ScreenIntro.png", Texture.class);
        assetManager.load("topBar.png", Texture.class);
        assetManager.load("tower-green.png", Texture.class);
        assetManager.load("tower-red.png", Texture.class);
        assetManager.load("Owens_Frank.jpg", Texture.class);
        assetManager.load("settings.png", Texture.class);
        Gdx.app.log(tag, "Game assets loaded.");
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
        assetManager.finishLoading();
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
        assetManager.dispose();
        super.dispose();
    }
}
