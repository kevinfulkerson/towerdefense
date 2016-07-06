package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.graphics.camera.CameraManipulationSystem;

public class GameScreen implements Screen {
    private GalacticTDGame gameInstance;
    private World gameWorld;
    private OrthographicCamera gameCamera;
    private FitViewport gameViewport;
    private GestureDetector gestureDetector;

    public GameScreen(GalacticTDGame game) {
        gameInstance = game;
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(1920, 1080, gameCamera);
        gameViewport.apply(true);
    }

    /**
     * Sets up the world with all its systems.
     * Only fires if world has not been instantiated yet.
     */
    private void initiateWorld() {
        if (gameWorld == null) {
            WorldConfiguration gameWorldConfig = new WorldConfiguration();

            CameraManipulationSystem gameCameraManipulator = new CameraManipulationSystem(gameCamera, gameViewport);
            gameWorldConfig.setSystem(gameCameraManipulator);
            gestureDetector = new GestureDetector(gameCameraManipulator);

            gameWorld = new World(gameWorldConfig);
        }
    }

    @Override
    public void show() {
        initiateWorld();
        Gdx.input.setInputProcessor(gestureDetector);
    }

    @Override
    public void render(float delta) {
        gameWorld.setDelta(delta);
        gameWorld.process();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
