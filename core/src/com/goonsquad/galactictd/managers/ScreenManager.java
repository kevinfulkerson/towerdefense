package com.goonsquad.galactictd.managers;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.screens.LoadingScreen;

public class ScreenManager {

    private final String tag = "ScreenManager";
    private static ScreenManager manager;
    private static Lock creationLock = new ReentrantLock();

    private final ArrayList<Screen> gameScreens;
    private Screen currentScreen;

    public static ScreenManager instance() {
        if (creationLock.tryLock()) {
            try {
                if (manager == null) {
                    manager = new ScreenManager();
                }
            } finally {
                creationLock.unlock();
            }
        }
        return manager;
    }

    private ScreenManager() {
        Gdx.app.log(this.tag, "Initialized ScreenManager.");

        this.gameScreens = new ArrayList<Screen>();

        final LoadingScreen loadingScreen = new LoadingScreen();
        this.gameScreens.add(loadingScreen);
    }

    public void setScreen(Class<? extends Screen> newScreen) {
        for (Screen s : this.gameScreens) {
            if (s.getClass().equals(newScreen)) {
                GalacticTDGame.instance().setScreen(s);
                this.currentScreen = s;
            }
        }
    }

    public void dispose() {
        this.manager = null;
        for (Screen screen : this.gameScreens) {
            screen.dispose();
        }
    }
}

