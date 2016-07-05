package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    public AssetManager manager;

    public Assets() {
        this.manager = new AssetManager();
    }

    public void loadAssets() {
        loadLoadingScreenAssets();
        loadNixieFont();
    }

    private void loadLoadingScreenAssets() {
        //Put assets here that are needed on loading screen.
        //Calling finish loading will insure all the needed assets are loaded.
        manager.finishLoading();
    }

    private void loadNixieFont() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter loaderParams = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loaderParams.fontFileName = "NixieOne.ttf";
        loaderParams.fontParameters.size = 48;
        loaderParams.fontParameters.borderColor = Color.WHITE;
        loaderParams.fontParameters.borderWidth = 1.3f;
        manager.load("nixie48.ttf", BitmapFont.class, loaderParams);
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
