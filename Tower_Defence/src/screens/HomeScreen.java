package screens;

import gamelogic.ScreenState;
import towerdefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomeScreen implements Screen {

	private SpriteBatch batch;
	private Sprite backgroundSprite, playButton, quitButton, scoresButton, howToPlayButton, title;

	private float w, h;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.justTouched()) {
			int y = Gdx.input.getY();
			int x = Gdx.input.getX();
			
			if (playButton.getBoundingRectangle().contains(x, y)){
				this.pause();
				TowerDefense.instance().setScreen(ScreenState.GRID_SCREEN.getScreen());
			} else if (quitButton.getBoundingRectangle().contains(x, y)){
				Gdx.app.exit();
			} else if (scoresButton.getBoundingRectangle().contains(x, y)){
				this.pause();
				TowerDefense.instance().setScreen(ScreenState.SCORES_SCREEN.getScreen());
			} else if (howToPlayButton.getBoundingRectangle().contains(x, y)){
				this.pause();
				TowerDefense.instance().setScreen(ScreenState.RULES_SCREEN.getScreen());
			}
		}

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

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		backgroundSprite = new Sprite(TowerDefense.instance().getAssetManager().get("space2.jpg", Texture.class));
		backgroundSprite.setSize(w, h);

		playButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonPlay.png", Texture.class));
		playButton.setBounds((w * 1/3), (h * 9/24), (w * 1/3), (h * 1/3));

		quitButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonQuit.png", Texture.class));
		quitButton.setBounds((w * 10/24), (h * 1/24), (w * 2/12), (h * 2/12));
		
		scoresButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonScore.png", Texture.class));
		scoresButton.setBounds((w  * 5/24), (h * 4/24), (w * 2/12), (h * 2/12));
		
		howToPlayButton = new Sprite(TowerDefense.instance().getAssetManager().get("buttonIntro.png", Texture.class));
		howToPlayButton.setBounds((w * 15/24), (h * 4/24), (w * 2/12), (h * 2/12));
		
		title = new Sprite(TowerDefense.instance().getAssetManager().get("galacticTD.png", Texture.class));
		title.setBounds((w * 1/3), (h * 15/24), (w * 1/3), (h * 1/3));
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
		backgroundSprite.getTexture().dispose();
		playButton.getTexture().dispose();
		quitButton.getTexture().dispose();
		scoresButton.getTexture().dispose();
		howToPlayButton.getTexture().dispose();
		title.getTexture().dispose();
		batch.dispose();
	}

}
