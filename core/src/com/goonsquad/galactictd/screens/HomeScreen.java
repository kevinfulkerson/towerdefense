package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archetypes.HomeScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.state.ShowOverlaySystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.HomeScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;
import com.goonsquad.galactictd.systems.positional.ResetPositionSystem;

public class HomeScreen implements Screen {
    private static final String TAG = "HomeScreen";
    private World homeScreenWorld;
    private GalacticTDGame gameInstance;
    private InputMultiplexer inputMultiplexer;

    public HomeScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        inputMultiplexer = new InputMultiplexer();
    }

    //A WorldConfig is used to build a world so that dependency injection can occur.
    public void createWorld() {
        if (homeScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();
            worldConfig.setSystem(new HomeScreenArchetypeBuilder());
            worldConfig.setSystem(new HomeScreenInitSystem(gameInstance));

            UiTouchSystem uiTouchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(uiTouchSystem);

            worldConfig.setSystem(new ResetPositionSystem());
            worldConfig.setSystem(new MoveToPointSystem());

            worldConfig.setSystem(new BoxRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new ShowOverlaySystem());
            /*
            When a new world is created, it will tell the passed in instance of worldconfig to go through all
            the set systems and do the following for each system:
            Inject dependencies that are tagged with @Wire. Mappers and System objects are automatically injected.
            Set the current system's world variable to the world that was instantiated.
            Call the system's initialize() method.
            */
            homeScreenWorld = new World(worldConfig);

            inputMultiplexer.addProcessor(uiTouchSystem);
        }
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
        createWorld();
        Gdx.input.setInputProcessor(inputMultiplexer);
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
        if (homeScreenWorld != null) {
            homeScreenWorld.dispose();
        }
        Gdx.app.log(TAG, "dispose() called.");
    }
}

