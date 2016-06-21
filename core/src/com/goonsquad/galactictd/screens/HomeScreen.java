package com.goonsquad.galactictd.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.goonsquad.galactictd.GalacticTDGame;

public class HomeScreen implements Screen, InputProcessor {
    private static final String TAG = "HomeScreen";
    private GalacticTDGame gameInstance;
    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Sprite playButton;
    private Sprite quitButton;
    private Sprite scoresButton;
    private Sprite settingsButton;
    private Sprite title;
    private Sprite settingsBackdrop;
    private Sprite settingsMenu;
    private Vector2 settingsMenuEnd;
    private Vector2 settingsMenuStart;
    private final float settingsMenuSpeed = 1f;
    private Sprite vibrateButton;
    private Sprite soundButton;
    private boolean settingsOn;
    private boolean loaded;

    public HomeScreen(GalacticTDGame game) {
        Gdx.app.log(TAG, "Initialized " + TAG);
        this.gameInstance = game;
        batch = new SpriteBatch();
        this.settingsOn = false;
        this.loaded = false;
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called.");
        Gdx.input.setInputProcessor(this);
        if (!loaded) loadScreenObjects();
    }

    public void loadScreenObjects() {
        createHomeScreenObjects();
        setHomeScreenSpriteBounds(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
        createSettingsObjects();
        setSettingsSpriteBounds(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
        this.loaded = true;
    }

    private void createHomeScreenObjects() {
        title = new Sprite(gameInstance.getAssetManager().get("galacticTD.png", Texture.class));
        backgroundSprite = new Sprite(gameInstance.getAssetManager().get("space2.jpg", Texture.class));
        playButton = new Sprite(gameInstance.getAssetManager().get("buttonPlay.png", Texture.class));
        quitButton = new Sprite(gameInstance.getAssetManager().get("buttonQuit.png", Texture.class));
        scoresButton = new Sprite(gameInstance.getAssetManager().get("buttonScore.png", Texture.class));
        settingsButton = new Sprite(gameInstance.getAssetManager().get("settings.png", Texture.class));
    }

    private void setHomeScreenSpriteBounds(float width, float height) {
        Vector2 buttonSize = new Vector2(300f, 150f);
        backgroundSprite.setSize(width, height);
        playButton.setBounds((width * 1f / 3f), (height * 3f / 8f), (width * 1f / 3f), (height * 1f / 3f));
        quitButton.setBounds((width * 5f / 12f), (height * 1f / 24f), buttonSize.x, buttonSize.y);
        scoresButton.setBounds((width * 1f / 24f), (height * 1f / 8f), buttonSize.x, buttonSize.y);
        settingsButton.setBounds((width - (width * 1 / 24) - buttonSize.x), (height * 1 / 8), buttonSize.x, buttonSize.y);
        title.setBounds((width * 1 / 3), (height * 5 / 8), (width * 1 / 3), (height * 1 / 3));
    }

    private void createSettingsObjects() {
        settingsBackdrop = new Sprite(gameInstance.getAssetManager().get("black.png", Texture.class));
        settingsBackdrop.setAlpha(.8f);
        settingsMenu = new Sprite(gameInstance.getAssetManager().get("border.png", Texture.class));
    }

    private void setSettingsSpriteBounds(float width, float height) {
        settingsBackdrop.setSize(width, height);
        settingsMenu.setSize(width / 4f, width / 4f);
        settingsMenuEnd = new Vector2(width - settingsMenu.getWidth(), 0);
        settingsMenuStart = new Vector2(width, -settingsMenu.getHeight());
        settingsMenu.setPosition(settingsMenuStart.x, settingsMenuStart.y);
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();
        renderHome(batch);
        if (settingsOn) {
            updateSettings(delta);
            renderSettings(batch);
        }
        batch.end();
    }

    private void renderHome(SpriteBatch batch) {
        backgroundSprite.draw(batch);
        playButton.draw(batch);
        quitButton.draw(batch);
        scoresButton.draw(batch);
        settingsButton.draw(batch);
        title.draw(batch);
    }

    private void updateSettings(float delta) {
        
    }

    private void renderSettings(SpriteBatch batch) {
        settingsBackdrop.draw(batch);
        settingsMenu.draw(batch);
    }

    private boolean handleSettingsTouch(Vector3 touchLocation) {
        return toggleSettings();
    }

    private boolean toggleSettings() {
        settingsMenu.setPosition(settingsMenuStart.x, settingsMenuStart.y);
        settingsOn = !settingsOn;
        return true;
    }

    private boolean handleHomeTouch(Vector3 touchLocation) {
        if (playButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            return true;
        } else if (quitButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            Gdx.app.exit();
            return true;
        } else if (scoresButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            gameInstance.getScreenManager().setScreen(ScoreScreen.class);
            return true;
        } else if (settingsButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            return toggleSettings();
        }
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
            backgroundSprite.getTexture().dispose();
            playButton.getTexture().dispose();
            quitButton.getTexture().dispose();
            scoresButton.getTexture().dispose();
            settingsButton.getTexture().dispose();
            title.getTexture().dispose();
        }
    }
}

