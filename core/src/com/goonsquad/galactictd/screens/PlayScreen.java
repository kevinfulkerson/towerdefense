package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.PlayScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;

public class PlayScreen implements Screen {

    private static final String TAG = "PlayScreen";
    private GalacticTDGame gameInstance;
    private World playScreenWorld;
    private InputMultiplexer inputMultiplexer;

    public PlayScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, String.format("Constructed %s.", TAG));
        this.gameInstance = game;
        inputMultiplexer = new InputMultiplexer();
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
        this.createWorld();
        Gdx.input.setInputProcessor(inputMultiplexer); //TODO: create the input processor for this class
    }

    @Override
    public void render(float delta) {
        playScreenWorld.setDelta(delta);
        playScreenWorld.process();
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
        if(playScreenWorld != null) {
            playScreenWorld.dispose();
        }
    }

    private void createWorld() {
        if(playScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();
            worldConfig.setSystem(new PlayScreenArchetypeBuilder());
            worldConfig.setSystem(new PlayScreenInitSystem(this.gameInstance));

            UiTouchSystem uiTouchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(uiTouchSystem);

            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera()));

            worldConfig.setSystem(new MoveToPointSystem());

            playScreenWorld = new World(worldConfig);

            inputMultiplexer.addProcessor(uiTouchSystem);
        }
    }
}
