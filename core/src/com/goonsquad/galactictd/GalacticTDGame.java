package com.goonsquad.galactictd;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
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

    public GalacticTDGame() {
    }

    @Override
    public void create() {
        Gdx.app.log(tag, "create() called.");
        Gdx.input.setCatchBackKey(true);

        assetManager = new AssetManager();
        this.loadGameAssets();

        scoreManager = new ScoreManager(5);

        screenManager = new ScreenManager(this);

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(UI_WIDTH, UI_HEIGHT, uiCamera);
        uiViewport.apply(true);

        screenManager.setScreen(LoadingScreen.class);
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
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

    private void loadGameAssets() {
        Gdx.app.log(tag, "Loading game assets.");
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
        assetManager.load("space2.jpg", Texture.class);
        assetManager.load("topBar.png", Texture.class);
        assetManager.load("tower-green.png", Texture.class);
        assetManager.load("tower-red.png", Texture.class);
        assetManager.load("Owens_Frank.jpg", Texture.class);
        Gdx.app.log(tag, "Game assets loaded.");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height);
        super.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
        Gdx.app.log(tag, "dispose() called.");
        scoreManager.dispose();
        screenManager.dispose();
        assetManager.dispose();
        super.dispose();
    }
}
