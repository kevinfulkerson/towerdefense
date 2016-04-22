package screens;

import interfaces.IScreenInput;
import gamelogic.ScreenState;
import towerdefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen, IScreenInput{

	private Sprite loadingScreenSprite;
	private SpriteBatch batch;
	
	@Override
	public void render(float delta) {
		if (TowerDefense.instance().getAssetManager().update())
		{
			TowerDefense.instance().setScreen(ScreenState.HOME_SCREEN);
		}
		else
		{
			batch.begin();

			loadingScreenSprite.draw(batch);

			batch.end();
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		loadingScreenSprite = new Sprite(new Texture(Gdx.files.internal("loading.jpg")));
		loadingScreenSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		loadingScreenSprite.getTexture().dispose();
	}

	@Override
	public void handleTouchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		
	}

}
