package gamelogic;

import inputprocessing.HomeScreenProcessor;

import com.badlogic.gdx.InputProcessor;

public enum ScreenState {

	MAIN_SCREEN(0);

	private InputProcessor inputProcessor;

	private ScreenState(int screenType) 
	{
		switch (screenType) 
		{
		case 0:
		{
			this.inputProcessor = new HomeScreenProcessor();
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

}
