package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    public AssetManager manager;

    public Assets() {
        this.manager = new AssetManager();
    }

    /**
     * This method calls the methods that enqueue assets to be loaded.
     */
    public void loadAssets() {
        loadLoadingScreenAssets();
        loadFonts();
    }

    /**
     * This method enqueues all the assets needed in the loading screen and calls finishLoading()
     * to ensure the assets needed in the loading screen are available.
     */
    private void loadLoadingScreenAssets() {
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

    @Override
    public void dispose() {
        manager.dispose();
    }
}
