package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archetypes.ScoreScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.OutlineSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.ScoreScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;
import com.goonsquad.galactictd.systems.positional.ResetPositionSystem;

public class ScoreScreen implements Screen {
    private static final String TAG = "ScoreScreen";

    private GalacticTDGame gameInstance;
    private World scoreScreenWorld;
    private UiTouchSystem touchSystem;

    public ScoreScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
    }

    private void createWorld() {
        Gdx.app.debug(TAG, "createWorld() called");
        if (scoreScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();

            worldConfig.setSystem(new ScoreScreenArchetypeBuilder());
            worldConfig.setSystem(new ScoreScreenInitSystem(gameInstance));

            touchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(touchSystem);

            worldConfig.setSystem(new OutlineSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera(), gameInstance.assets.manager.get("Owens_Frank.jpg", Texture.class)));
            worldConfig.setSystem(new ResetPositionSystem());
            worldConfig.setSystem(new MoveToPointSystem());
            scoreScreenWorld = new World(worldConfig);
        }
    }

    @Override
    public void render(float delta) {
        scoreScreenWorld.setDelta(delta);
        scoreScreenWorld.process();
    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "show() called");
        createWorld();
        Gdx.input.setInputProcessor(touchSystem);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug(TAG, "resize() called");
    }

    @Override
    public void hide() {
        Gdx.app.debug(TAG, "hide() called");
    }

    @Override
    public void pause() {
        Gdx.app.debug(TAG, "pause() called");
    }

    @Override
    public void resume() {
        Gdx.app.debug(TAG, "resume() called");
    }

    @Override
    public void dispose() {
        Gdx.app.debug(TAG, "dispose() called");
        if (scoreScreenWorld != null)
            scoreScreenWorld.dispose();
    }
}

