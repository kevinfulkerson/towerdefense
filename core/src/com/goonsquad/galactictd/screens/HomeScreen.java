package com.goonsquad.galactictd.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.goonsquad.galactictd.GalacticTDGame;

public class HomeScreen implements Screen, InputProcessor {
    private final String tag = "HomeScreen";
    private GalacticTDGame gameInstance;
    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Sprite playButton;
    private Sprite quitButton;
    private Sprite scoresButton;
    private Sprite howToPlayButton;
    private Sprite title;
    private boolean loaded;

    public HomeScreen(GalacticTDGame game) {
        Gdx.app.log(tag, "Initialized " + tag);
        this.gameInstance = game;
        batch = new SpriteBatch();
        this.loaded = false;
    }

    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameInstance.getUiCamera().combined);
        batch.begin();

        backgroundSprite.draw(batch);
        playButton.draw(batch);
        quitButton.draw(batch);
        scoresButton.draw(batch);
        howToPlayButton.draw(batch);
        title.draw(batch);

        batch.end();
    }

    @Override
    public void show() {
        Gdx.app.log(tag, "show() called.");
        Gdx.input.setInputProcessor(this);
        if (!loaded) loadScreenObjects();
    }

    public void loadScreenObjects() {
        backgroundSprite = new Sprite(gameInstance.getAssetManager().get("space2.jpg", Texture.class));
        playButton = new Sprite(gameInstance.getAssetManager().get("buttonPlay.png", Texture.class));
        quitButton = new Sprite(gameInstance.getAssetManager().get("buttonQuit.png", Texture.class));
        scoresButton = new Sprite(gameInstance.getAssetManager().get("buttonScore.png", Texture.class));
        howToPlayButton = new Sprite(gameInstance.getAssetManager().get("settings.png", Texture.class));
        title = new Sprite(gameInstance.getAssetManager().get("galacticTD.png", Texture.class));
        this.setSpriteBounds(GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);
        this.loaded = true;
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(tag, "resize() called.");
    }

    @Override
    public void hide() {
        Gdx.app.log(tag, "hide() called");
    }

    @Override
    public void pause() {
        Gdx.app.log(tag, "pause() called");
    }

    @Override
    public void resume() {
        Gdx.app.log(tag, "resume() called");
    }

    @Override
    public void dispose() {
        Gdx.app.log(tag, "dispose() called.");
        batch.dispose();
        if (loaded) {
            backgroundSprite.getTexture().dispose();
            playButton.getTexture().dispose();
            quitButton.getTexture().dispose();
            scoresButton.getTexture().dispose();
            howToPlayButton.getTexture().dispose();
            title.getTexture().dispose();
        }
    }

    private void setSpriteBounds(float width, float height) {
        Vector2 buttonSize = new Vector2(300f, 150f);
        backgroundSprite.setSize(width, height);
        playButton.setBounds((width * 1f / 3f), (height * 3f / 8f), (width * 1f / 3f), (height * 1f / 3f));
        quitButton.setBounds((width * 5f / 12f), (height * 1f / 24f), buttonSize.x, buttonSize.y);
        scoresButton.setBounds((width * 1f / 24f), (height * 1f / 8f), buttonSize.x, buttonSize.y);
        Gdx.app.log("scores x is", scoresButton.getX() + "");
        howToPlayButton.setBounds((width - (width * 1 / 24) - buttonSize.x), (height * 1 / 8), buttonSize.x, buttonSize.y);
        title.setBounds((width * 1 / 3), (height * 5 / 8), (width * 1 / 3), (height * 1 / 3));
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
        Vector3 touchLocation = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        touchLocation = gameInstance.getUiViewport().unproject(touchLocation);
        Gdx.app.log("Touch x is", touchLocation.x + "");
        Gdx.app.log("Score's x is", scoresButton.getX() + "");
        if (playButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            GalacticTDGame.instance().setScreen(ScreenState.GRID_SCREEN);
        } else if (quitButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            Gdx.app.exit();
        } else if (scoresButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
            gameInstance.getScreenManager().setScreen(ScoreScreen.class);
        } else if (howToPlayButton.getBoundingRectangle().contains(touchLocation.x, touchLocation.y)) {
//            GalacticTDGame.instance().setScreen(ScreenState.RULES_SCREEN);
            Gdx.app.log("HomeScreen", "settings pressed");
        }
        return true;
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
}

