package inputprocessing;

import interfaces.IScreenInput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public class HomeScreenProcessor implements InputProcessor{
	
	private IScreenInput view;
	
	public HomeScreenProcessor(IScreenInput view)
	{
		this.view = view;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stubz
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// Translate the coordinates from input coordinate system 
		// into graphics coordinate system
		int y = (int) ((screenY - Gdx.graphics.getHeight()) * -1);
		// This coordinate is already correct
		int x = screenX;
		
		this.view.handleTouchDown(x, y, pointer, button);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
