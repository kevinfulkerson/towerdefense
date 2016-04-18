package screens;

import java.util.ArrayList;

import logging.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

import Tower.Defence.TowerDefence;

public class ScoresScreen implements Screen {

	private int state = 1;
	private static final int PAUSED = 1, PLAYING = 0;
	private TowerDefence game;
	private SpriteBatch batch;
	private Sprite background, sprite1, sprite2;
	private static float width, height;
	private FileHandle scoresDataFile;
	private String scoresAsString;
	private ArrayList<Integer> highScores = new ArrayList<Integer>();
	private String[] stringArray;
	private BitmapFont text;
	private boolean setUp = false;

	public ScoresScreen(TowerDefence towerDefence) {
		this.game = towerDefence;
	}

	@Override
	public void render(float delta) {
		if (state == PAUSED) {
			this.resume();
		}

		if (Gdx.input.justTouched()) {
			this.pause();
			game.setScreen(game.homeScreen);
		}

		sprite1.translateX(-9);
		sprite2.translateX(7);

		if (sprite1.getX() < -width / 10) {
			sprite1.setPosition(width, (height - (width / 6)));
		}
		if (sprite2.getX() > width) {
			sprite2.setPosition(-width / 10, height / 6);
		}

		batch.begin();

		background.draw(batch);
		sprite1.draw(batch);
		sprite2.draw(batch);

		text.draw(batch, "HIGH SCORES:", (width / 10), (height / 16) * 15);
		text.draw(batch, "" + highScores.get(0), (width / 10) * 6, (height / 16) * 15);
		text.draw(batch, "" + highScores.get(1), (width / 10) * 6, (height / 16) * 13);
		text.draw(batch, "" + highScores.get(2), (width / 10) * 6, (height / 16) * 11);
		text.draw(batch, "" + highScores.get(3), (width / 10) * 6, (height / 16) * 9);
		text.draw(batch, "" + highScores.get(4), (width / 10) * 6, (height / 16) * 7);

		batch.end();
	}

	@Override
	public void show() {
		if (!setUp) {
			setUp = true;
			batch = new SpriteBatch();

			width = Gdx.graphics.getWidth();
			height = Gdx.graphics.getHeight();
			background = new Sprite(TowerDefence.manager.get("space2.jpg", Texture.class));
			background.setBounds(0, 0, width, height);

			sprite1 = new Sprite(TowerDefence.manager.get("tower-green.png", Texture.class));
			sprite1.rotate90(true);
			sprite1.rotate90(true);
			sprite1.rotate90(true);
			sprite1.setBounds(width, (height - (width / 6)), width / 10, width / 10);

			sprite2 = new Sprite(TowerDefence.manager.get("tower-red.png", Texture.class));
			sprite2.rotate90(true);
			sprite2.setBounds(-width / 10, height / 6, width / 10, width / 10);

			getScoresFile();
			
			Gdx.app.log(Constants.LOG, "score screen initialized");
			
			text = new BitmapFont();
			text.scale(2f);
		}
	}

	public void sendScore(int score) {
		getScoresFile();
		sortScores();
		int newScore = newHighScore(score);
		if (newScore != 5) {
			highScores.add(newScore, score);
			highScores.remove(5);
		}
		scoresDataFile.writeString(highScores.get(0) + " " + highScores.get(1) + " " + highScores.get(2) + " "
				+ highScores.get(3) + " " + highScores.get(4), false);
	}

	private int newHighScore(int score) {
		for (int i : highScores) {
			if (score > i) {
				return highScores.indexOf(i);
			}
		}
		return 5;
	}

	public void getScoresFile() {
		try {
			scoresDataFile = Gdx.files.local("highScores.txt");
			scoresAsString = scoresDataFile.readString();
		} catch (GdxRuntimeException e) {
			scoresDataFile = Gdx.files.local("highScores.txt");
			scoresDataFile.writeString("0 0 0 0 0", false);
			scoresDataFile = Gdx.files.local("highScores.txt");
			scoresAsString = scoresDataFile.readString();
		}
		stringArray = scoresAsString.split(" ");
		for (int i = 0; i < 5; i++) {
			highScores.add(Integer.parseInt(stringArray[i]));
		}
		sortScores();
		// for (int s : highScores) {
		// System.out.println(s);
		// }
	}

	public void sortScores() {
		int[] temp = new int[5];
		for (int i = 0; i < 5; i++) {
			temp[i] = highScores.remove(0);
		}

		for (int j = 0; j < 5; j++) {
			for (int k = 0; k < 5; k++) {
				if (temp[j] > temp[k]) {
					int holder = temp[j];
					temp[j] = temp[k];
					temp[k] = holder;
				}
			}
		}

		for (int s : temp) {
			highScores.add(s);
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
		state = PAUSED;
		// System.out.println("Paused");
	}

	@Override
	public void resume() {
		state = PLAYING;
		// System.out.println("Resumed");
		scoresDataFile = null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
