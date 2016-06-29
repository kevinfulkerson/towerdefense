package com.goonsquad.galactictd.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.systems.archtypes.HomeScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.graphics.BoxRenderSystem;
import com.goonsquad.galactictd.systems.graphics.UiRenderSystem;
import com.goonsquad.galactictd.systems.initialization.HomeScreenInitSystem;
import com.goonsquad.galactictd.systems.input.UiTouchSystem;

public class HomeScreen implements Screen, InputProcessor {
    private static final String TAG = "HomeScreen";
    private World homeScreenWorld;
    private GalacticTDGame gameInstance;
    private SpriteBatch batch;
    private Sprite quitButton;
    private Sprite settingsBackdrop;
    private Sprite settingsMenu;
    private Vector2 settingsMenuEnd;
    private Vector2 settingsMenuStart;
    private final float settingsMenuSpeed = 1f;
    private float settingsMenuElapsedTime = 0f;
    private Sprite soundButton;
    private boolean settingsOn;
    private boolean loaded;
    private UiTouchSystem uiTouchSystem;

    public HomeScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
        this.settingsOn = false;
        this.loaded = false;
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

    public void loadScreenObjects() {
        createHomeScreenObjects();
        setHomeScreenSpriteBounds(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
        createSettingsObjects();
        setSettingsSpriteBounds(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
        this.loaded = true;
    }

    private void createHomeScreenObjects() {
        quitButton = new Sprite(gameInstance.getAssetManager().get("buttonQuit.png", Texture.class));

    }

    private void setHomeScreenSpriteBounds(float width, float height) {
        Vector2 buttonSize = new Vector2(300f, 150f);

    }

    private void createSettingsObjects() {
        settingsBackdrop = new Sprite(gameInstance.getAssetManager().get("black.png", Texture.class));
        settingsBackdrop.setAlpha(.8f);
        settingsMenu = new Sprite(gameInstance.getAssetManager().get("border.png", Texture.class));
        soundButton = new Sprite(gameInstance.getAssetManager().get("music_icon.png", Texture.class));
        soundButton.setColor(255, 255, 255, 255);
    }

    private void setSettingsSpriteBounds(float width, float height) {
        settingsBackdrop.setSize(width, height);

        settingsMenu.setSize(width / 4f, width / 4f);
        settingsMenuEnd = new Vector2(width - settingsMenu.getWidth(), 0);
        settingsMenuStart = new Vector2(width, -settingsMenu.getHeight());
        settingsMenu.setPosition(settingsMenuStart.x, settingsMenuStart.y);

        soundButton.setSize(width / 16f, width / 16f);
        soundButton.setPosition(settingsMenuEnd.x + soundButton.getWidth(), settingsMenu.getHeight() - soundButton.getHeight());
    }

    @Override
    public void render(float delta) {
//        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
//        batch.begin();
//        renderHome(batch);
//        if (settingsOn) {
//            updateSettings(delta);
//            renderSettings(batch);
//        }
//        batch.end();
        homeScreenWorld.setDelta(delta);
        homeScreenWorld.process();
    }

    private void renderHome(SpriteBatch batch) {
        quitButton.draw(batch);
    }

    private void updateSettings(float delta) {
        if (settingsMenuElapsedTime < settingsMenuSpeed) {
            settingsMenuElapsedTime += delta;
            float movementAmount = MathUtils.clamp(settingsMenuElapsedTime, 0, settingsMenuSpeed);

            float newX = MathUtils.lerp(settingsMenu.getX(), settingsMenuEnd.x, movementAmount);
            float newY = MathUtils.lerp(settingsMenu.getY(), settingsMenuEnd.y, movementAmount);

            settingsMenu.setPosition(newX, newY);
        }
    }

    private void renderSettings(SpriteBatch batch) {
        settingsBackdrop.draw(batch);
        settingsMenu.draw(batch);
        soundButton.draw(batch);
    }

    private boolean handleSettingsTouch(Vector3 touchLocation) {
        return toggleSettings();
    }

    private boolean toggleSettings() {
        settingsMenu.setPosition(settingsMenuStart.x, settingsMenuStart.y);
        settingsMenuElapsedTime = 0f;
        settingsOn = !settingsOn;
        return true;
    }

    private boolean handleHomeTouch(Vector3 touchLocation) {
//        if (playButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            return true;
//        } else if (quitButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            Gdx.app.exit();
//            return true;
//        }
//        else if (scoresButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            gameInstance.getScreenManager().setScreen(ScoreScreen.class);
//            return true;
//        }
//        else if (settingsButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            return toggleSettings();
//        }
        return false;
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchLocation = new Vector3(screenX, screenY, 0);
        touchLocation = gameInstance.getUiViewport().unproject(touchLocation);
        if (settingsOn) {
            return handleSettingsTouch(touchLocation);
        } else {
            return handleHomeTouch(touchLocation);
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose() called.");
        batch.dispose();
        if (loaded) {
            quitButton.getTexture().dispose();
        }
    }
}

