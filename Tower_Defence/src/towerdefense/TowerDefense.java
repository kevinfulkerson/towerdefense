package towerdefense;

import gamelogic.ScreenState;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import managers.InputProcessorManager;
import managers.ScreenStateManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class TowerDefense extends Game {
	
	//https://github.com/libgdx/libgdx/blob/master/demos/superjumper/superjumper/src/com/badlogicgames/superjumper/GameScreen.java
	
	private static TowerDefense instance;
	private static Lock creationLock = new ReentrantLock();
	
	private AssetManager assetManager = new AssetManager();
	
	private TowerDefense()
	{
		assetManager.load("blankTextBorder.png", Texture.class);
		assetManager.load("border.png", Texture.class);
		assetManager.load("borderSelected.png", Texture.class);
		assetManager.load("buttonBuy.png", Texture.class);
		assetManager.load("buttonCancel.png", Texture.class);
		assetManager.load("buttonIntro.png", Texture.class);
		assetManager.load("buttonPlay.png", Texture.class);
		assetManager.load("buttonQuit.png", Texture.class);
		assetManager.load("buttonScore.png", Texture.class);
		assetManager.load("enemy1.png", Texture.class);
		assetManager.load("enemy2.png", Texture.class);
		assetManager.load("fireBall.png", Texture.class);
		assetManager.load("galacticTD.png", Texture.class);
		assetManager.load("loading.jpg", Texture.class);
		assetManager.load("mapDefault.png", Texture.class);
		assetManager.load("mapSelected.png", Texture.class);
		assetManager.load("pathLeft.png", Texture.class);
		assetManager.load("pathRight.png", Texture.class);
		assetManager.load("pathStraight.png", Texture.class);
		assetManager.load("ScreenIntro.png", Texture.class);
		assetManager.load("space2.jpg", Texture.class);
		assetManager.load("topBar.png", Texture.class);
		assetManager.load("tower-green.png", Texture.class);
		assetManager.load("tower-red.png", Texture.class);
		assetManager.load("Owens_Frank.jpg", Texture.class);
	}
	
	public static TowerDefense instance()
	{
		if(creationLock.tryLock())
		{
			try
			{
				if(instance == null)
				{
					instance = new TowerDefense();
				}
			} 
			finally
			{
				creationLock.unlock();
			}
		}
		return instance;
	}
	
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		Texture.setEnforcePotImages(false);
		this.setScreen(ScreenState.LOADING_SCREEN);
		
		Gdx.input.setInputProcessor(InputProcessorManager.instance());
	}
	
	public void setScreen(ScreenState screen) {
		// TODO Auto-generated method stub
		ScreenStateManager.instance().setCurrentScreenState(screen);
		super.setScreen(screen.getScreen());
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
		assetManager.dispose();
		ScreenStateManager.instance().dispose();
		super.dispose();
	}
	
	public AssetManager getAssetManager()
	{
		return assetManager;
	}
	
}
