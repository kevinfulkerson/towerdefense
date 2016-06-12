package com.goonsquad.galactictd.gamelogic;

//import com.goonsquad.galactictd.screens.*;
//import com.goonsquad.galactictd.inputprocessing.HomeScreenProcessor;
//import com.goonsquad.galactictd.interfaces.IScreenInput;

import com.badlogic.gdx.InputProcessor;

public enum ScreenState {

    LOADING_SCREEN(0),
    HOME_SCREEN(1),
    GRID_SCREEN(2),
    RULES_SCREEN(3),
    SCORES_SCREEN(4);

//    private IScreenInput screen;
    private InputProcessor inputProcessor;

    private ScreenState(int screenType)
    {
        switch (screenType)
        {
            case 0:
            {
//                this.screen = new LoadingScreen();
//                this.inputProcessor = new HomeScreenProcessor(this.screen);

            }
            break;

            case 1:
            {
//                this.screen = new HomeScreen();
//                this.inputProcessor = new HomeScreenProcessor(this.screen);

            }
            break;

            case 2:
            {
//                this.screen = new GridScreen();
//                this.inputProcessor = new HomeScreenProcessor(this.screen);
            }
            break;

            case 3:
            {
//                this.screen = new RulesScreen();
//                this.inputProcessor = new HomeScreenProcessor(this.screen);

            }
            break;

            case 4:
            {
//                this.screen = new ScoresScreen();
//                this.inputProcessor = new HomeScreenProcessor(this.screen);

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

//    public IScreenInput getScreen()
//    {
//        return this.screen;
//    }

    public void dispose()
    {
        this.inputProcessor = null;
//        this.screen.dispose();
    }
}

