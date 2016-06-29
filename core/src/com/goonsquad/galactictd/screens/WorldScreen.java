package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.archetypesdeprecated.ArchetypeSheet;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.input.MoveToTouchSystem;
import com.goonsquad.galactictd.systems.positional.MovementSystem;
import com.goonsquad.galactictd.systems.initialization.WorldScreenInitSystemOld;
import com.goonsquad.galactictd.systems.graphics.GameRenderSystem;

public class WorldScreen implements Screen {
    private World world;
    private Camera gameCamera;
    private Viewport gameViewport;
    private GalacticTDGame gameInstance;

    public WorldScreen(GalacticTDGame game) {
        this.gameInstance = game;
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(800, 450, gameCamera);
        gameViewport.apply(true);
    }

    @Override
    public void show() {
        initializeScreen();
    }

    private void initializeScreen() {
        if (world == null) {
            initializeWorld();
        }
    }


    private void initializeWorld() {
        ArchetypeSheet archetypeSheet = null;
        MoveToTouchSystem moveToTouchSystem = new MoveToTouchSystem(gameViewport);
        world = new World(
                new WorldConfiguration()
                        .setSystem(new WorldScreenInitSystemOld(archetypeSheet, gameInstance))
                        .setSystem(MovementSystem.class)
                        .setSystem(new GameRenderSystem(gameCamera))
                        .setSystem(new BoxRenderSystem(gameCamera))
                        .setSystem(moveToTouchSystem)
        );
        Gdx.input.setInputProcessor(moveToTouchSystem);
    }

    @Override
    public void render(float delta) {
        world.setDelta(delta);
        world.process();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
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
