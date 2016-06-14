package com.goonsquad.galactictd.managers;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.screens.HomeScreen;
import com.goonsquad.galactictd.screens.LoadingScreen;
import com.goonsquad.galactictd.screens.ScoreScreen;

public class ScreenManager {

    private final String tag = "ScreenManager";
    private static ScreenManager manager;
    private static Lock creationLock = new ReentrantLock();

    private final ArrayList<Screen> gameScreens;

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

        final HomeScreen homeScreen = new HomeScreen();
        this.gameScreens.add(homeScreen);

        final ScoreScreen scoreScreen = new ScoreScreen();
        this.gameScreens.add(scoreScreen);
    }

    public void setScreen(Class<? extends Screen> newScreen) {
        for (Screen s : this.gameScreens) {
            if (s.getClass().equals(newScreen)) {
                GalacticTDGame.instance().setScreen(s);
            }
        }
    }

    public void dispose() {
        for (Screen screen : this.gameScreens) {
            screen.dispose();
        }
    }
}

