package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.GameRenderSystem;
import com.goonsquad.galactictd.systems.graphics.OutlineSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.PlayScreenInitSystem;
import com.goonsquad.galactictd.systems.input.GameTouchSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;

public class PlayScreen implements Screen {
    public static final float GAME_WIDTH = 1920;
    public static final float GAME_HEIGHT = 1080;
    private static final String TAG = "PlayScreen";
    private GalacticTDGame gameInstance;
    private World playScreenWorld;
    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private InputMultiplexer inputMultiplexer;
    private Texture defaultTexture;

    public PlayScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, String.format("Constructed %s.", TAG));
        this.gameInstance = game;
        inputMultiplexer = new InputMultiplexer();
        gameCamera = new OrthographicCamera();
        gameViewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, gameCamera);
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
        this.gameViewport.update(width, height, true);
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
        if (playScreenWorld != null) {
            playScreenWorld.dispose();
        }
    }

    private void createWorld() {
        if (playScreenWorld == null) {
            defaultTexture = gameInstance.assets.manager.get("Owens_Frank.jpg", Texture.class);
            WorldConfiguration worldConfig = new WorldConfiguration();
            //Init Systems
            worldConfig.setSystem(new PlayScreenArchetypeBuilder());
            worldConfig.setSystem(new PlayScreenInitSystem(this.gameInstance));
            //Input Systems
            UiTouchSystem uiTouchSystem = new UiTouchSystem(gameInstance.getUiViewport());
            worldConfig.setSystem(uiTouchSystem);
            GameTouchSystem gameTouchSystem = new GameTouchSystem(gameViewport);
            worldConfig.setSystem(gameTouchSystem);
            //Update Systems
            worldConfig.setSystem(new MoveToPointSystem());
            //Render Systems
            worldConfig.setSystem(new GameRenderSystem(gameCamera, defaultTexture));
            worldConfig.setSystem(new UiRenderSystem(gameInstance.getUiCamera(), defaultTexture));
            worldConfig.setSystem(new OutlineSystem(gameInstance.getUiCamera()));

            playScreenWorld = new World(worldConfig);
            inputMultiplexer.addProcessor(uiTouchSystem);
            inputMultiplexer.addProcessor(gameTouchSystem);
        }
    }
}
