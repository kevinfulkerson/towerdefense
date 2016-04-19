package gamelogic;

import screens.HomeScreen;
import screens.LoadingScreen;
import towerdefense.TowerDefense;
import inputprocessing.HomeScreenProcessor;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public enum ScreenState {

	LOADING_SCREEN(0),
	HOME_SCREEN(1);

	private Screen screen;
	private InputProcessor inputProcessor;

	private ScreenState(int screenType) 
	{
		switch (screenType) 
		{
		case 0:
		{
			this.inputProcessor = new HomeScreenProcessor();
			this.screen = new LoadingScreen();
		}
		break;
		
		case 1:
		{
			this.inputProcessor = new HomeScreenProcessor();
			this.screen = new HomeScreen(TowerDefense.instance());
		}
		break;
		
		default:
			throw new IndexOutOfBoundsException("Invalid ScreenState screenType");
		}
	}
	
	public InputProcessor getInputProcessor()
	{
		return this.inputProcessor;
	}
	
	public Screen getScreen()
	{
		return this.screen;
	}

}
