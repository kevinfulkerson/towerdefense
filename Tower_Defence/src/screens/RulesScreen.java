package screens;

import gamelogic.ScreenState;
import towerdefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RulesScreen implements Screen{

	private SpriteBatch batch;
	private Sprite background, text;
	private static float width, height;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if (Gdx.input.justTouched()) {
			TowerDefense.instance().setScreen(ScreenState.HOME_SCREEN.getScreen());
		}
		
		batch.begin();

		background.draw(batch);
		text.draw(batch);
		
		batch.end();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
		batch = new SpriteBatch();
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		background = new Sprite(TowerDefense.assetManager.get("space2.jpg", Texture.class));
		background.setBounds(0, 0, width, height);
		
		text = new Sprite(TowerDefense.assetManager.get("ScreenIntro.png", Texture.class));
		text.setBounds((width * 1/12), (height * 1/12), (width * 5/6), (height * 5/6));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		
	}

}
