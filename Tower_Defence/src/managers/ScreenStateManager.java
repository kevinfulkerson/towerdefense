package managers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gamelogic.ScreenState;

public class ScreenStateManager {

	private static ScreenStateManager manager;
	private static Lock creationLock = new ReentrantLock();
	
	private ScreenState currentScreenState;
	
	private ScreenStateManager()
	{
		// default current screen to the Loading Screen
		this.currentScreenState = ScreenState.LOADING_SCREEN;
	}
	
	public static ScreenStateManager instance()
	{
		if(creationLock.tryLock())
		{
			try
			{
				if(manager == null)
				{
					manager = new ScreenStateManager();
				}
			} 
			finally
			{
				creationLock.unlock();
			}
		}
		return manager;
	}
	
	public ScreenState getCurrentScreenState()
	{
		return this.currentScreenState;
	}
	
	public void setCurrentScreenState(ScreenState newState)
	{
		this.currentScreenState = newState;
	}
	
	public void dispose()
	{
		this.currentScreenState = null;
		for(ScreenState ss :ScreenState.values())
		{
			ss.dispose();
		}
	}
}
