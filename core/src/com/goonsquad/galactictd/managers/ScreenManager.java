package com.goonsquad.galactictd.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.screens.HomeScreen;
import com.goonsquad.galactictd.screens.LoadingScreen;
import com.goonsquad.galactictd.screens.ScoreScreen;

import java.util.ArrayList;

public class ScreenManager {
    private static final String tag = "ScreenManager";
    private final ArrayList<Screen> gameScreens;

    private GalacticTDGame gameInstance;

    public ScreenManager(GalacticTDGame game) {
        Gdx.app.log(tag, "Initialized ScreenManager.");
        this.gameInstance = game;
        this.gameScreens = new ArrayList<Screen>();

        final LoadingScreen loadingScreen = new LoadingScreen(game);
        this.gameScreens.add(loadingScreen);

        final HomeScreen homeScreen = new HomeScreen(game);
        this.gameScreens.add(homeScreen);

        final ScoreScreen scoreScreen = new ScoreScreen(game);
        this.gameScreens.add(scoreScreen);

    }

    public void setScreen(Class<? extends Screen> newScreen) {
        for (Screen s : this.gameScreens) {
            if (s.getClass().equals(newScreen)) {
                gameInstance.setScreen(s);
            }
        }
    }

    public void dispose() {
        for (Screen screen : this.gameScreens) {
            screen.dispose();
        }
    }
}

