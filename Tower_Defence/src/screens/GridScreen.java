package screens;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import logging.Constants;
import renderables.Enemy;
import renderables.Entity;
import renderables.Projectile;
import renderables.Tower;
import renderables.Wave;
import towerdefense.Player;
import towerdefense.TowerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import exceptions.InvalidActionOnEntityException;
import gamelogic.Grid;
import gamelogic.Road;

public class GridScreen implements Screen {

	public static TowerDefense game;
	
	private static final int GAME_PAUSED = 0, GAME_RUNNING = 1;
	private static int state = 1;
	private float scaleFactorX, scaleFactorY;
	private boolean canPerformSafeTilt = false;
	private byte selectedTower;
	
	private SpriteBatch batch;
	private Sprite background;
	private ArrayList<Entity> drawableEntities, projectiles, trash;

	private boolean isSetup = false;

	public static Grid grid;
	public static Road road;
	public static float screenWidth, screenHeight, screenOffset, menuHeight;
	private Sprite offsetOverlay;

	//Sprites needed for Tower Selection
	private Sprite overlay;
	private Sprite towerOptionOne;
	private Sprite towerOptionTwo;
	private Sprite cancelButton;
	private Sprite buyButton;
	private Sprite towerToolTip;
	private Sprite towerOneMenuImage;
	private Sprite towerTwoMenuImage;
	
	private Wave wave;

	public static Vector2 gridClickedLocation = new Vector2(-1, -1);
	
	public static Player player;
	
	private BitmapFont display, towerOneStats, towerTwoStats, menuMessage;

	// ----------------------End Variables----------------------
	// ---------------------------------------------------------
	// ---------------------------------------------------------
	// ----------------------Begin Methods----------------------

	public GridScreen(TowerDefense game) {
		GridScreen.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (state == GAME_RUNNING) {
			updateWhileRunning(delta);
			wave.doTheWave(drawableEntities, delta);
		} else if (state == GAME_PAUSED) {
			updateWhilePaused();
		}
		
		batch.begin();

		background.draw(batch);
		grid.draw(batch);
		
		for (Entity e : drawableEntities) {
			e.sprite.draw(batch);
		}
		
		for (Entity e : projectiles) {
			e.sprite.draw(batch);
		}
		
		if (state == GAME_PAUSED) {
			overlay.draw(batch);
			towerOptionOne.draw(batch);
			towerOptionTwo.draw(batch);
			buyButton.draw(batch);
			cancelButton.draw(batch);
			towerOneMenuImage.draw(batch);
			towerTwoMenuImage.draw(batch);
			if(selectedTower == 0){
				menuMessage.draw(batch, "Select a Tower to purchase.",screenHeight* 2/16, (menuHeight * 6/16));
			}
			else if(selectedTower == 1){
				towerToolTip.draw(batch);
				towerOneStats.draw(batch, "Range: " + Tower.getTowerRange(selectedTower), (screenHeight * 35/32), (menuHeight* 29/32));
				towerOneStats.draw(batch, "Damage: " + Tower.getTowerDamage(selectedTower), (screenHeight * 35/32),(menuHeight * 26/32));
				towerOneStats.draw(batch, "Attack Speed: " + Tower.getTowerAttackSpeed(selectedTower), (screenHeight * 35/32),(menuHeight*23/32));
				towerOneStats.draw(batch, "Cost: " + Tower.towerCost(selectedTower) + " gold", (screenHeight * 35/32),(menuHeight * 20/32));
				if (!player.canAfford(Tower.towerCost(1))){
					menuMessage.draw(batch, "You can not afford this Tower",screenHeight* 2/16, (menuHeight * 6/16));
				}
				
			}
			else if(selectedTower == 2){
				towerToolTip.draw(batch);
				towerTwoStats.draw(batch, "Range: " + Tower.getTowerRange(selectedTower), (screenHeight * 35/32), (menuHeight* 29/32));
				towerTwoStats.draw(batch, "Damage: " + Tower.getTowerDamage(selectedTower), (screenHeight * 35/32), (menuHeight * 26/32));
				towerTwoStats.draw(batch, "Attack Speed: " + Tower.getTowerAttackSpeed(selectedTower),(screenHeight * 35/32), (menuHeight * 23/32));
				towerOneStats.draw(batch, "Cost: " + Tower.towerCost(selectedTower) + " gold", (screenHeight * 35/32),(menuHeight * 20/32));
				if (!player.canAfford(Tower.towerCost(2))){
					menuMessage.draw(batch, "You can not afford this Tower",screenHeight* 2/16, (menuHeight * 6/16));
				}
			}
		}
		offsetOverlay.draw(batch);
		display.draw(batch, "Lives : " + player.getLives(), (screenWidth * 1/24), screenHeight - (screenOffset/3));
		display.draw(batch, "Gold : " + player.getGold(), (screenWidth * 7/24), screenHeight - (screenOffset/3));
		display.draw(batch, "Score : " + player.getScore(), (screenWidth * 13/24), screenHeight - (screenOffset/3));
		display.draw(batch, "Wave Number : " + wave.waveNumber, (screenWidth * 19/24), screenHeight - (screenOffset/3));

		batch.end();
	}

	@Override
	public void show() {
		if (!isSetup) {
			Texture.setEnforcePotImages(false);
			batch = new SpriteBatch();
			screenWidth = Gdx.graphics.getWidth();
			screenHeight = Gdx.graphics.getHeight();
			screenOffset = screenHeight / 15;
			menuHeight = screenHeight - screenOffset;

			road = new Road(TowerDefense.assetManager);
			grid = new Grid(TowerDefense.assetManager, screenWidth, screenHeight - screenOffset, 10, 5, road);
			
			drawableEntities = new ArrayList<Entity>();
			projectiles = new ArrayList<Entity>();
			trash = new ArrayList<Entity>();
			background = new Sprite(TowerDefense.assetManager.get("space2.jpg", Texture.class));
			scaleFactorX = (background.getWidth() / (screenWidth + 110));
			scaleFactorY = (background.getHeight() / (screenHeight + 110));
			if (scaleFactorX >= 1.15f)
				scaleFactorX = 1.15f;
			if (scaleFactorY >= 1.15f)
				scaleFactorY = 1.15f;
			Gdx.app.log(Constants.LOG, screenHeight + " " + screenWidth);
			Gdx.app.log(Constants.LOG, scaleFactorX + " " + scaleFactorY);
			Gdx.app.log(Constants.LOG, String.valueOf(scaleFactorX * (screenWidth + 110)));
			Gdx.app.log(Constants.LOG, String.valueOf(scaleFactorY * (screenHeight + 110)));
			background.setSize((scaleFactorX * (screenWidth + 110)), (scaleFactorY * (screenHeight + 110)));
			canPerformSafeTilt = (Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer) && (scaleFactorY >= 1.0f) && (scaleFactorX >= 1.0f));
			
			offsetOverlay = new Sprite(TowerDefense.assetManager.get("topBar.png", Texture.class));
			offsetOverlay.setBounds(0, screenHeight-screenOffset, screenWidth, screenOffset);
			
			overlay = new Sprite(TowerDefense.assetManager.get("border.png", Texture.class));
			overlay.setBounds(0, 0, screenHeight, menuHeight);
			
			towerOptionOne = new Sprite(TowerDefense.assetManager.get("border.png",Texture.class));
			towerOptionOne.setBounds((screenHeight * 3/32), (menuHeight * 15/32), (screenHeight * 3/8), (screenHeight * 3/8));
			
			towerOneMenuImage = new Sprite(TowerDefense.assetManager.get("tower-green.png",Texture.class));
			towerOneMenuImage.setBounds(screenHeight * 5/32, (menuHeight * 9/16), (screenHeight/4), (screenHeight/4));
			
			towerOptionTwo = new Sprite(TowerDefense.assetManager.get("border.png",Texture.class));
			towerOptionTwo.setBounds((screenHeight*17/32), (menuHeight * 15/32), (screenHeight * 3/8), (screenHeight * 3/8));
			
			towerTwoMenuImage = new Sprite(TowerDefense.assetManager.get("tower-red.png",Texture.class));
			towerTwoMenuImage.setBounds((screenHeight*19/32),(menuHeight*9/16),(screenHeight/4),(screenHeight/4));
			
			buyButton = new Sprite(TowerDefense.assetManager.get("buttonBuy.png",Texture.class));
			buyButton.setBounds((screenHeight * 3/32), menuHeight/8, (screenHeight * 3/8), (menuHeight * 3/16));
			
			cancelButton = new Sprite(TowerDefense.assetManager.get("buttonCancel.png",Texture.class));
			cancelButton.setBounds((screenHeight * 17/32), (menuHeight/8), (screenHeight * 3/8), (menuHeight * 3/16));
			
			towerToolTip = new Sprite(TowerDefense.assetManager.get("border.png",Texture.class));
			towerToolTip.setBounds(screenHeight * 33/32, menuHeight * 15/32, screenHeight/2, menuHeight/2);
			
			
			wave = new Wave();
			player = new Player(100);
			
			int textScale = (screenWidth < 800) ? 0 : (screenWidth > 1500) ? 1 : 0;
			display = new BitmapFont();
			display.scale(textScale);
			
			menuMessage = new BitmapFont();
			menuMessage.scale(textScale);
			towerOneStats = new BitmapFont();
			towerOneStats.scale(textScale);
			towerTwoStats = new BitmapFont();
			towerTwoStats.scale(textScale);
			
			isSetup = true;
		} else {
			this.resume();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
//		System.out.println("Paused");
		Gdx.app.log(Constants.LOG, "Paused");
		state = GAME_PAUSED;
	}

	@Override
	public void resume() {
//		System.out.println("Running");
		Gdx.app.log(Constants.LOG, "Running");
		state = GAME_RUNNING;
	}

	@Override
	public void dispose() {
		if (isSetup) {
			for (Entity e : drawableEntities) {
				e.sprite.getTexture().dispose();
			}
			grid.dispose();
			background.getTexture().dispose();
			batch.dispose();
		}
	}

	public void updateWhileRunning(float delta) {
		if (player.playerDead()){
			isSetup = false;
			game.scoresScreen.sendScore(player.getScore());
			game.setScreen(game.homeScreen);
		}
		
		processInputs();
		updateEntities(delta);
		reOrderEntitiesForRendering();
	}

	public void updateWhilePaused() {
		if(Gdx.input.justTouched()){
			
			if(spriteWasSelected(towerOptionOne))
			{
				Gdx.input.vibrate(20);
				selectedTower = 1;
				towerOptionOne.setTexture(TowerDefense.assetManager.get("borderSelected.png", Texture.class));
				towerOptionTwo.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
			} else if(spriteWasSelected(towerOptionTwo))
			{
				Gdx.input.vibrate(20);
				selectedTower = 2;
				towerOptionOne.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
				towerOptionTwo.setTexture(TowerDefense.assetManager.get("borderSelected.png", Texture.class));
			}
			
			if(spriteWasSelected(cancelButton))
			{
				Gdx.input.vibrate(20);
				towerOptionOne.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
				towerOptionTwo.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
				selectedTower = 0;
				grid.resetTile();
				this.resume();
			} else if(spriteWasSelected(buyButton)){
				if (!grid.getGridTile(gridClickedLocation.x, gridClickedLocation.y).isOccupied) {
					if(selectedTower != 0){
						switch(selectedTower){
						case 1: 
							if(player.canAfford(Tower.towerCost(1))){
								addNewTower(selectedTower);
							}
							break;
						case 2:
							if(player.canAfford(Tower.towerCost(2))){
								addNewTower(selectedTower);
							}
							break;
						default:
							Gdx.input.vibrate(new long[] {0, 20, 80, 20}, -1);
						}
					}
				}
			}
		}
		if (canPerformSafeTilt) {
			performBackgroundTilt();
		}
	}

	public void processInputs() {

		if (Gdx.input.justTouched() && Gdx.input.isTouched(0)) {
			Gdx.app.log(Constants.LOG, "justTouched");
			if (grid.toggleClick()) {
				Gdx.input.vibrate(20);
				this.pause();
			} else {
				Gdx.input.vibrate(new long[] {0, 20, 80, 20}, -1);
			}
		}

		if(Gdx.input.getInputProcessor().keyDown(Input.Keys.W)){
			this.pause();
			game.setScreen(game.homeScreen);
		}

		if (canPerformSafeTilt) {
			performBackgroundTilt();
		}

	}

	public void updateEntities(float delta) {
		for (Entity e1 : drawableEntities) {
			e1.moveSprite();
			if (e1 instanceof Tower) {
				Tower t1 = (Tower) e1;
				for (Entity e2 : drawableEntities) {
					if(e2 instanceof Enemy && t1.distanceBetween(e2) < t1.range){
						try {
							t1.setFacingToEntity(e2);
							if (t1.canFire(delta)) {
								projectiles.add(t1.shoot(e2));
							}
							break;
						} catch (InvalidActionOnEntityException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
		for (Entity p : projectiles) {
			if (((Projectile) p).trackTarget()) {
				trash.add(p);
			}
		}
		for (Entity t : trash) {
			projectiles.remove(t);
		}
		trash.clear();
	}

	public void reOrderEntitiesForRendering() {
		try {
			for (Entity e : drawableEntities) {
				if (e.state == Entity.STATE_DEAD) {
					player.addGold(e.worth);
					player.addScore(e.score);
					player.unitKilled();
					wave.aliveUnits--;
					drawableEntities.remove(e);
				}
			}
		} catch (ConcurrentModificationException e) {
			//nothing to do, actually
		}
	}
	
	public void addNewTower(byte towerOption){
		switch(towerOption){
		case 1: 
			drawableEntities.add(new Tower(TowerDefense.assetManager.get("tower-green.png", Texture.class), gridClickedLocation, 1));
			break;
		case 2:
			drawableEntities.add(new Tower(TowerDefense.assetManager.get("tower-red.png", Texture.class), gridClickedLocation, 2));
			break;
		}
		
		player.buy(Tower.towerCost(towerOption));
		
		grid.getGridTile(gridClickedLocation.x, gridClickedLocation.y).isOccupied = true;
		grid.resetTile();
		this.resume();
		Gdx.input.vibrate(20);
		selectedTower = 0;
		towerOptionOne.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
		towerOptionTwo.setTexture(TowerDefense.assetManager.get("border.png", Texture.class));
	}
	
	public boolean spriteWasSelected(Sprite selectedSprite){
		int yInput = (int) (screenHeight - Gdx.input.getY());
		int xInput = Gdx.input.getX();
		
		return (xInput > selectedSprite.getBoundingRectangle().x
				&& xInput < selectedSprite.getBoundingRectangle().x + selectedSprite.getBoundingRectangle().width
				&& yInput > selectedSprite.getBoundingRectangle().y
				&& yInput < selectedSprite.getBoundingRectangle().y + selectedSprite.getBoundingRectangle().height);
	}
	
	public void performBackgroundTilt(){
		float positionX = -(Gdx.input.getAccelerometerY() * 5 + (background.getWidth() - Gdx.graphics.getWidth())
				/ (2 * scaleFactorX));
		float positionY = -(-Gdx.input.getAccelerometerX() * 5 + (background.getHeight() - Gdx.graphics.getHeight())
				/ (2 * scaleFactorY));
		background.setPosition(positionX, positionY);
	}
	

}
