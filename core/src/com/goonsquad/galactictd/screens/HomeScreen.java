package com.goonsquad.galactictd.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.managers.ScreenManager;

public class HomeScreen implements Screen, InputProcessor {
    private final String tag = "HomeScreen";

    private SpriteBatch batch;
    private Sprite backgroundSprite;
    private Sprite playButton;
    private Sprite quitButton;
    private Sprite scoresButton;
    private Sprite howToPlayButton;
    private Sprite title;
    private boolean loaded;

    public HomeScreen() {
        Gdx.app.log(tag, "Initialized " + tag);
        batch = new SpriteBatch();
        this.loaded = false;
    }

    @Override
    public void render(float delta) {
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
        backgroundSprite = new Sprite(GalacticTDGame.instance().getAssetManager().get("space2.jpg", Texture.class));
        playButton = new Sprite(GalacticTDGame.instance().getAssetManager().get("buttonPlay.png", Texture.class));
        quitButton = new Sprite(GalacticTDGame.instance().getAssetManager().get("buttonQuit.png", Texture.class));
        scoresButton = new Sprite(GalacticTDGame.instance().getAssetManager().get("buttonScore.png", Texture.class));
        howToPlayButton = new Sprite(GalacticTDGame.instance().getAssetManager().get("buttonIntro.png", Texture.class));
        title = new Sprite(GalacticTDGame.instance().getAssetManager().get("galacticTD.png", Texture.class));
        this.loaded = true;
    }

    @Override
    public void resize(int width, int height) {
        this.setSpriteBounds(width, height);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
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
        backgroundSprite.setSize(width, height);
        playButton.setBounds((width * 1 / 3), (height * 3 / 8), (width * 1 / 3), (height * 1 / 9));
        quitButton.setBounds((width * 5 / 12), (height * 5 / 24), (width * 1 / 6), (height * 1 / 6));
        scoresButton.setBounds((width * 5 / 24), (height * 1 / 6), (width * 1 / 6), (height * 1 / 6));
        howToPlayButton.setBounds((width * 5 / 8), (height * 1 / 6), (width * 1 / 6), (height * 1 / 6));
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
        int y = (int) ((screenY - Gdx.graphics.getHeight()) * -1);
        if (playButton.getBoundingRectangle().contains(screenX, y)) {
//            GalacticTDGame.instance().setScreen(ScreenState.GRID_SCREEN);
        } else if (quitButton.getBoundingRectangle().contains(screenX, y)) {
            Gdx.app.exit();
        } else if (scoresButton.getBoundingRectangle().contains(screenX, y)) {
            ScreenManager.instance().setScreen(ScoreScreen.class);
        } else if (howToPlayButton.getBoundingRectangle().contains(screenX, y)) {
//            GalacticTDGame.instance().setScreen(ScreenState.RULES_SCREEN);
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

