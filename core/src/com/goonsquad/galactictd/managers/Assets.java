package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    private static final String TAG = "Assets";
    public AssetManager manager;

    public Assets() {
        this.manager = new AssetManager();
    }

    /**
     * This method calls the methods that enqueue assets to be loaded.
     */
    public void loadAssets() {
        loadLoadingScreenAssets();
        loadImages();
        loadFonts();
    }

    /**
     * This method enqueues all the assets needed in the loading screen and calls finishLoading()
     * to ensure the assets needed in the loading screen are available.
     */
    private void loadLoadingScreenAssets() {
        manager.load("loading.jpg", Texture.class);
        manager.finishLoading();
    }

    /**
     * Loads the fonts used in the game.
     */
    private void loadFonts() {
        FreeTypeFontParameter nixieParams = new FreeTypeFontParameter();
        nixieParams.borderColor = Color.WHITE;
        nixieParams.borderWidth = 1.3f;
        nixieParams.size = 48;
        generateAndLoadTrueTypeFont("NixieOne.ttf", "nixie48.ttf", nixieParams);
    }

    /**
     * Adds a true type font to the asset manager.
     *
     * @param fontFileName      The file name of the true type font used to generate this font.
     * @param fontReferenceName The name that will be used to reference the created font from within the program.
     * @param fontParams        The params that specify things like size, color, border width.
     */
    public void generateAndLoadTrueTypeFont(String fontFileName, String fontReferenceName, FreeTypeFontParameter fontParams) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter loaderParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loaderParams.fontFileName = fontFileName;
        loaderParams.fontParameters = fontParams;
        manager.load(fontReferenceName, BitmapFont.class, loaderParams);
    }

    private void loadImages() {
        Gdx.app.log(TAG, "Loading game assets.");
        manager.load("star.png", Texture.class);
        manager.load("music_icon.png", Texture.class);
        manager.load("black.png", Texture.class);
        manager.load("blankTextBorder.png", Texture.class);
        manager.load("border.png", Texture.class);
        manager.load("borderSelected.png", Texture.class);
        manager.load("buttonBuy.png", Texture.class);
        manager.load("buttonCancel.png", Texture.class);
        manager.load("buttonIntro.png", Texture.class);
        manager.load("buttonPlay.png", Texture.class);
        manager.load("buttonQuit.png", Texture.class);
        manager.load("buttonScore.png", Texture.class);
        manager.load("enemy1.png", Texture.class);
        manager.load("enemy2.png", Texture.class);
        manager.load("fireBall.png", Texture.class);
        manager.load("galacticTD.png", Texture.class);
        manager.load("loading.jpg", Texture.class);
        manager.load("mapDefault.png", Texture.class);
        manager.load("mapSelected.png", Texture.class);
        manager.load("pathLeft.png", Texture.class);
        manager.load("pathRight.png", Texture.class);
        manager.load("pathStraight.png", Texture.class);
        manager.load("ScreenIntro.png", Texture.class);
        manager.load("topBar.png", Texture.class);
        manager.load("tower-green.png", Texture.class);
        manager.load("tower-red.png", Texture.class);
        manager.load("Owens_Frank.jpg", Texture.class);
        manager.load("settings.png", Texture.class);
        Gdx.app.log(TAG, "Game assets loaded.");
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
