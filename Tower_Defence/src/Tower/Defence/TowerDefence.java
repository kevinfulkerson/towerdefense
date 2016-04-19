package Tower.Defence;

import managers.InputProcessorManager;
import screens.GridScreen;
import screens.HomePage;
import screens.RulesScreen;
import screens.ScoresScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class TowerDefence extends Game {
	
	public HomePage homeScreen = new HomePage(this);
	public GridScreen gridScreen = new GridScreen(this);
	public ScoresScreen scoresScreen = new ScoresScreen(this);
	public RulesScreen rulesScreen = new RulesScreen(this);
	public static AssetManager manager = new AssetManager();
	public InputProcessor processor;
	
	//https://github.com/libgdx/libgdx/blob/master/demos/superjumper/superjumper/src/com/badlogicgames/superjumper/GameScreen.java
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		Texture.setEnforcePotImages(false);
		this.setScreen(homeScreen);
		
		manager.load("blankTextBorder.png", Texture.class);
		manager.load("border.png", Texture.class);
		manager.load("borderSelected.png", Texture.class);
		manager.load("buttonBuy.png", Texture.class);
		manager.load("buttonCancel.png", Texture.class);
		manager.load("buttonIntro.png", Texture.class);
		manager.load("buttonPlay.png", Texture.class);
		manager.load("buttonQuit.png", Texture.class);
		manager.load("buttonScore.png", Texture.class);
		manager.load("enemy1.png", Texture.class);
		manager.load("enemy2.png", Texture.class);
		manager.load("fireBall.png", Texture.class);
		manager.load("galacticTD.png", Texture.class);
		manager.load("loading.jpg", Texture.class);
		manager.load("mapDefault.png", Texture.class);
		manager.load("mapSelected.png", Texture.class);
		manager.load("pathLeft.png", Texture.class);
		manager.load("pathRight.png", Texture.class);
		manager.load("pathStraight.png", Texture.class);
		manager.load("ScreenIntro.png", Texture.class);
		manager.load("space2.jpg", Texture.class);
		manager.load("topBar.png", Texture.class);
		manager.load("tower-green.png", Texture.class);
		manager.load("tower-red.png", Texture.class);
		manager.load("Owens_Frank.jpg", Texture.class);
		
		Gdx.input.setInputProcessor(InputProcessorManager.instance());
	}
	
	@Override //delete this line if issue, maybe
	public void render(){
		
		super.render();
	}

	@Override
	public void pause() {
//		this.dispose();
	}

	@Override
	public void dispose() {
		homeScreen.dispose();
		gridScreen.dispose();
		manager.dispose();
		super.dispose();
	}
	
}
