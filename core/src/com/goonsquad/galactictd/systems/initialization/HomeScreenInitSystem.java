package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.screens.ScoreScreen;
import com.goonsquad.galactictd.systems.archtypes.HomeScreenArchetypeBuilder;

public class HomeScreenInitSystem extends InitializationSystem {
    private HomeScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;

    private GalacticTDGame gameInstance;
    private Vector2 buttonSize;

    public HomeScreenInitSystem(GalacticTDGame game) {
        this.gameInstance = game;
        buttonSize = new Vector2(300f, 150f);
    }

    @Override
    protected void populateWorld() {
        createTitle();
        createPlayButton();
        createScoreButton();
        createQuitButton();
        createSettingsButton();
    }

    private void createTitle() {
        int title = archetypeBuilder.buildArchetype("ui_label");

        Renderable titleRenderable = renderableComponentMapper.get(title);
        titleRenderable.texture = gameInstance.getAssetManager().get("galacticTD.png", Texture.class);

        Position titlePosition = positionComponentMapper.get(title);
        titlePosition.setBounds(
                (GalacticTDGame.UI_WIDTH / 3f), (GalacticTDGame.UI_HEIGHT * 5 / 8),
                (GalacticTDGame.UI_WIDTH / 3f), (GalacticTDGame.UI_HEIGHT / 3f));
    }

    private void createPlayButton() {
        int playButton = archetypeBuilder.buildArchetype("ui_button");

        Renderable playRenderable = renderableComponentMapper.get(playButton);
        playRenderable.texture = gameInstance.getAssetManager().get("buttonPlay.png", Texture.class);

        Position playPosition = positionComponentMapper.get(playButton);
        playPosition.setBounds(
                (GalacticTDGame.UI_WIDTH * 1f / 3f), (GalacticTDGame.UI_HEIGHT * 3f / 8f),
                (GalacticTDGame.UI_WIDTH * 1f / 3f), (GalacticTDGame.UI_HEIGHT * 1f / 3f));

        Touchable touchable = touchableComponentMapper.get(playButton);
        touchable.event = new Event() {
            @Override
            public void fireEvent() {
                Gdx.app.log("PlayButton", "Touched");
            }
        };
    }

    private void createScoreButton() {
        int scoreButton = archetypeBuilder.buildArchetype("ui_button");

        Renderable scoreRenderable = renderableComponentMapper.get(scoreButton);
        scoreRenderable.texture = gameInstance.getAssetManager().get("buttonScore.png", Texture.class);

        Position scorePosition = positionComponentMapper.get(scoreButton);
        scorePosition.setBounds(
                (GalacticTDGame.UI_WIDTH * (1f / 24f)), (GalacticTDGame.UI_HEIGHT * (1f / 8f)),
                (buttonSize.x), (buttonSize.y));

        Touchable touchable = touchableComponentMapper.get(scoreButton);
        touchable.event = new Event() {
            @Override
            public void fireEvent() {
                gameInstance.getScreenManager().setScreen(ScoreScreen.class);
            }
        };
    }


    private void createQuitButton() {
        int quitButton = archetypeBuilder.buildArchetype("ui_button");

        Renderable quitRenderable = renderableComponentMapper.get(quitButton);
        quitRenderable.texture = gameInstance.getAssetManager().get("buttonQuit.png", Texture.class);

        Position quitPosition = positionComponentMapper.get(quitButton);
        quitPosition.setBounds(
                (GalacticTDGame.UI_WIDTH * (5f / 12f)), (GalacticTDGame.UI_HEIGHT / 24f),
                (buttonSize.x), (buttonSize.y));

        Touchable touchable = touchableComponentMapper.get(quitButton);
        touchable.event = new Event() {
            @Override
            public void fireEvent() {
                Gdx.app.exit();
            }
        };
    }

    private void createSettingsButton() {
        int settingsButton = archetypeBuilder.buildArchetype("ui_button");

        Renderable quitRenderable = renderableComponentMapper.get(settingsButton);
        quitRenderable.texture = gameInstance.getAssetManager().get("settings.png", Texture.class);

        Position quitPosition = positionComponentMapper.get(settingsButton);
        quitPosition.setBounds(
                (GalacticTDGame.UI_WIDTH - (GalacticTDGame.UI_WIDTH / 24f) - buttonSize.x), (GalacticTDGame.UI_HEIGHT / 8f),
                (buttonSize.x), (buttonSize.y));

        Touchable touchable = touchableComponentMapper.get(settingsButton);
        touchable.event = new Event() {
            @Override
            public void fireEvent() {
                Gdx.app.log("SettingsButton", "Touched");
            }
        };
    }
}
