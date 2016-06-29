package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archtypes.HomeScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.HomeScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;

public class HomeScreen implements Screen {
    private static final String TAG = "HomeScreen";
    private World homeScreenWorld;
    private GalacticTDGame gameInstance;
    private UiTouchSystem uiTouchSystem;

    public HomeScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
    }

    //A WorldConfig is used to build a world so that dependency injection can occur.
    public void createWorld() {
        if (homeScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();
            worldConfig.setSystem(new HomeScreenArchetypeBuilder());
            worldConfig.setSystem(new HomeScreenInitSystem(gameInstance));
            uiTouchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(uiTouchSystem);
            worldConfig.setSystem(new BoxRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera()));
            /*
            When a new world is created, it will tell its instance of worldconfig to go through all
            the set systems and do the following:
            Inject dependencies that are tagged with @Wire, Mappers and Systems are automatically injected.
            Set the current system's world dependency to the world that was instantiated.
            Call the system's initialize() method.
            */
            homeScreenWorld = new World(worldConfig);
        }
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
        createWorld();
        Gdx.input.setInputProcessor(uiTouchSystem);
    }

    @Override
    public void render(float delta) {
        homeScreenWorld.setDelta(delta);
        homeScreenWorld.process();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize() called.");
    }

    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide() called");
    }

    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause() called");
    }

    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume() called");
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose() called.");
    }
}

