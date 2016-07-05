package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archetypes.ScoreScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.ScoreScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;
import com.goonsquad.galactictd.systems.positional.ResetPositionSystem;

public class ScoreScreen implements Screen {
    private static final String TAG = "ScoreScreen";

    private GalacticTDGame gameInstance;
    private BitmapFont text;
    private World scoreScreenWorld;
    private UiTouchSystem touchSystem;

    public ScoreScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        generateFont();
    }

    private void createWorld() {
        Gdx.app.debug(TAG, "createWorld() called");
        if (scoreScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();

            worldConfig.setSystem(new ScoreScreenArchetypeBuilder());
            worldConfig.setSystem(new ScoreScreenInitSystem(gameInstance, text));

            touchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(touchSystem);

            worldConfig.setSystem(new BoxRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new ResetPositionSystem());
            worldConfig.setSystem(new MoveToPointSystem());
            scoreScreenWorld = new World(worldConfig);
        }
    }

    private void generateFont() {
        Gdx.app.debug(TAG, "generateFont() called");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NixieOne.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.WHITE;
        parameter.borderWidth = 1.3f;
        text = generator.generateFont(parameter);
        generator.dispose();
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
        text.dispose();
        if (scoreScreenWorld != null)
            scoreScreenWorld.dispose();
    }
}

