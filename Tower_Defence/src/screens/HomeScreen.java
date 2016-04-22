package screens;

import interfaces.IScreenInput;
import gamelogic.ScreenState;
import towerdefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeScreen implements Screen, IScreenInput {

	private SpriteBatch batch;
	private Sprite backgroundSprite;
	private Sprite playButton;
	private Sprite quitButton;
	private Sprite scoresButton;
	private Sprite howToPlayButton;
	private Sprite title;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();

		backgroundSprite = new Sprite(TowerDefense.instance().getAssetManager().get("space2.jpg", Texture.class));
		playButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonPlay.png", Texture.class));
		quitButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonQuit.png", Texture.class));
		scoresButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonScore.png", Texture.class));
		howToPlayButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonIntro.png", Texture.class));
		title = new Sprite(TowerDefense.instance().getAssetManager().get("galacticTD.png", Texture.class));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		this.setSpriteBounds(width, height);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		backgroundSprite.getTexture().dispose();
		playButton.getTexture().dispose();
		quitButton.getTexture().dispose();
		scoresButton.getTexture().dispose();
		howToPlayButton.getTexture().dispose();
		title.getTexture().dispose();
		batch.dispose();
	}
	
	@Override
	public void handleTouchDown(int screenX, int screenY, int pointer, int button) {
		if (playButton.getBoundingRectangle().contains(screenX, screenY))
		{
			this.pause();
			TowerDefense.instance().setScreen(ScreenState.GRID_SCREEN);
		} 
		else if (quitButton.getBoundingRectangle().contains(screenX, screenY))
		{
			Gdx.app.exit();
		} 
		else if (scoresButton.getBoundingRectangle().contains(screenX, screenY))
		{
			this.pause();
			TowerDefense.instance().setScreen(ScreenState.SCORES_SCREEN);
		} 
		else if (howToPlayButton.getBoundingRectangle().contains(screenX, screenY))
		{
			this.pause();
			TowerDefense.instance().setScreen(ScreenState.RULES_SCREEN);
		}
	}
	
	private void setSpriteBounds(float width, float height)
	{
		backgroundSprite.setSize(width, height);
		playButton.setBounds((width * 1/3), (height * 3/8), (width * 1/3), (height * 1/9));
		quitButton.setBounds((width * 5/12), (height * 5/24), (width * 1/6), (height * 1/6));
		scoresButton.setBounds((width  * 5/24), (height * 1/6), (width * 1/6), (height * 1/6));
		howToPlayButton.setBounds((width * 5/8), (height * 1/6), (width * 1/6), (height * 1/6));
		title.setBounds((width * 1/3), (height * 5/8), (width * 1/3), (height * 1/3));
	}
}
