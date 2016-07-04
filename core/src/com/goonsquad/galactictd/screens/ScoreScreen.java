package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.gamelogic.HighScore;
import com.goonsquad.galactictd.systems.archetypes.ScoreScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.ScoreScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;
import com.goonsquad.galactictd.systems.positional.ResetPositionSystem;

import java.util.ArrayList;

public class ScoreScreen implements Screen {
    private static final String TAG = "ScoreScreen";

    private GalacticTDGame gameInstance;
    private Vector2 textPosition;
    private SpriteBatch batch;
    private ArrayList<HighScore> highScoreArrayList;
    private BitmapFont text;
    private boolean loaded;
    private int textHeightFactor;
    private World scoreScreenWorld;
    private UiTouchSystem touchSystem;

    public ScoreScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
        generateFont();
        loaded = false;
    }

    private void createWorld() {
        Gdx.app.debug(TAG, "createWorld() called");
        if (scoreScreenWorld == null) {
            WorldConfiguration worldConfig = new WorldConfiguration();

            worldConfig.setSystem(new ScoreScreenArchetypeBuilder());
            worldConfig.setSystem(new ScoreScreenInitSystem(gameInstance));

            touchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(touchSystem);

            worldConfig.setSystem(new BoxRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera()));
            worldConfig.setSystem(new ResetPositionSystem());
            worldConfig.setSystem(new MoveToPointSystem());
            scoreScreenWorld = new World(worldConfig);
        }
    }

    private void loadScreenObjects() {
        Gdx.app.debug(TAG, "loadScreenObjects() called");
        textPosition = new Vector2(GalacticTDGame.UI_WIDTH / 5, GalacticTDGame.UI_HEIGHT / 20);
        loaded = true;
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
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();

        textHeightFactor = 15;
        text.draw(batch, "High Scores:", textPosition.x, textPosition.y * 15);
        for (HighScore highScore : highScoreArrayList) {
            text.draw(batch, "" + highScore.getScore(), textPosition.x * 3, textPosition.y * textHeightFactor);
            textHeightFactor -= 2;
        }
        batch.end();
    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "show() called");
        if (!loaded) loadScreenObjects();
        highScoreArrayList = gameInstance.getScoreManager().getScores();
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
        batch.dispose();
        if (scoreScreenWorld != null)
            scoreScreenWorld.dispose();
    }
}

