package interfaces;

import com.badlogic.gdx.Screen;

public interface IScreenInput extends Screen {
	
	public void handleTouchDown(int screenX, int screenY, int pointer, int button);

}
