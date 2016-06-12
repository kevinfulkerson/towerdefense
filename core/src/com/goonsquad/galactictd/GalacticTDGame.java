package com.goonsquad.galactictd;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.goonsquad.galactictd.managers.ScreenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.screens.LoadingScreen;

public class GalacticTDGame extends Game {

    private final String tag = "GalacticTDGame";
    private static GalacticTDGame instance;
    private static Lock creationLock = new ReentrantLock();
    private AssetManager assetManager = new AssetManager();

    private GalacticTDGame() {
    }

    public static GalacticTDGame instance() {
        if (creationLock.tryLock()) {
            try {
                if (instance == null) {
                    instance = new GalacticTDGame();
                }
            } finally {
                creationLock.unlock();
            }
        }
        return instance;
    }

    @Override
    public void create() {
        Gdx.app.log(tag, "create() called.");
        Gdx.input.setCatchBackKey(true);
        this.loadGameAssets();
        ScreenManager.instance().setScreen(LoadingScreen.class);
    }

    private void loadGameAssets() {
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
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        this.dispose();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        ScreenManager.instance().dispose();
        super.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
