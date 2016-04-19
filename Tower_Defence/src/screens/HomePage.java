package screens;

import Tower.Defence.TowerDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HomePage implements Screen {

	private TowerDefence game;
	private SpriteBatch batch;
	private Sprite backgroundSprite, loadingScreenSprite, playButton, quitButton, scoresButton, howToPlayButton, title;

	float w, h;
	private boolean isSetup = false, isBackgroundReady = false;

	public HomePage(TowerDefence game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		if (TowerDefence.manager.update()) {
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			if (!isBackgroundReady) {
				isBackgroundReady = true;
				backgroundSprite = new Sprite(TowerDefence.manager.get("space2.jpg", Texture.class));
				backgroundSprite.setSize(w, h);

				playButton = new Sprite(TowerDefence.manager.get("buttonPlay.png", Texture.class));
				playButton.setBounds((w * 1/3), (h * 9/24), (w * 1/3), (h * 1/3));

				quitButton = new Sprite(TowerDefence.manager.get("buttonQuit.png", Texture.class));
				quitButton.setBounds((w * 10/24), (h * 1/24), (w * 2/12), (h * 2/12));
				
				scoresButton = new Sprite(TowerDefence.manager.get("buttonScore.png", Texture.class));
				scoresButton.setBounds((w  * 5/24), (h * 4/24), (w * 2/12), (h * 2/12));
				
				howToPlayButton = new Sprite(TowerDefence.manager.get("buttonIntro.png", Texture.class));
				howToPlayButton.setBounds((w * 15/24), (h * 4/24), (w * 2/12), (h * 2/12));
				
				title = new Sprite(TowerDefence.manager.get("galacticTD.png", Texture.class));
				title.setBounds((w * 1/3), (h * 15/24), (w * 1/3), (h * 1/3));
			}
			
			if (Gdx.input.justTouched()) {
				int y = Gdx.input.getY();
				int x = Gdx.input.getX();
				
				if (playButton.getBoundingRectangle().contains(x, y)){
					this.pause();
					game.setScreen(game.gridScreen);
				} else if (quitButton.getBoundingRectangle().contains(x, y)){
					Gdx.app.exit();
				} else if (scoresButton.getBoundingRectangle().contains(x, y)){
					this.pause();
					game.setScreen(game.scoresScreen);
				} else if (howToPlayButton.getBoundingRectangle().contains(x, y)){
					this.pause();
					game.setScreen(game.rulesScreen);
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
		} else {
			batch.begin();

			loadingScreenSprite.draw(batch);

			batch.end();
		}

	}

	@Override
	public void show() {
		if (!isSetup) {
			Texture.setEnforcePotImages(false);
			batch = new SpriteBatch();

			w = Gdx.graphics.getWidth();
			h = Gdx.graphics.getHeight();

			loadingScreenSprite = new Sprite(new Texture(Gdx.files.internal("loading.jpg")));
			loadingScreenSprite.setSize(w, h);
			isSetup = true;
		}
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
		loadingScreenSprite.getTexture().dispose();
		backgroundSprite.getTexture().dispose();
		playButton.getTexture().dispose();
		quitButton.getTexture().dispose();
		scoresButton.getTexture().dispose();
		howToPlayButton.getTexture().dispose();
		title.getTexture().dispose();
		batch.dispose();
	}

}
