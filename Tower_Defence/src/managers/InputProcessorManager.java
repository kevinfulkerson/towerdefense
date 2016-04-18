package managers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputProcessorManager implements InputProcessor{

	private static InputProcessorManager manager;
	private static Lock creationLock = new ReentrantLock();
	
	private ScreenStateManager stateManager;
	
	private InputProcessorManager()
	{
		stateManager = ScreenStateManager.instance();
	}
	
	public static InputProcessorManager instance()
	{
		if(creationLock.tryLock())
		{
			try
			{
				if(manager == null)
				{
					manager = new InputProcessorManager();
				}
			} 
			finally
			{
				creationLock.unlock();
			}
		}
		return manager;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return stateManager.getCurrentScreenState().getInputProcessor().keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return stateManager.getCurrentScreenState().getInputProcessor().keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return stateManager.getCurrentScreenState().getInputProcessor().keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stateManager.getCurrentScreenState().getInputProcessor().touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stateManager.getCurrentScreenState().getInputProcessor().touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stateManager.getCurrentScreenState().getInputProcessor().touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return stateManager.getCurrentScreenState().getInputProcessor().mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		return stateManager.getCurrentScreenState().getInputProcessor().scrolled(amount);
	}
	

}
