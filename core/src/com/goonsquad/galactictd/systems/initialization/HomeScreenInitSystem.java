package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.layers.LayerLevel;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.screens.GameScreen;
import com.goonsquad.galactictd.screens.ScoreScreen;
import com.goonsquad.galactictd.systems.archetypes.HomeScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.state.ShowOverlaySystem;

public class HomeScreenInitSystem extends InitializationSystem {
    private HomeScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<MovementDestination> movementDestinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;
    private ShowOverlaySystem showOverlaySystem;

    private GalacticTDGame gameInstance;
    private float buttonWidth;
    private float buttonHeight;

    public HomeScreenInitSystem(GalacticTDGame game) {
        this.gameInstance = game;
        buttonWidth = 300f;
        buttonHeight = 150f;
    }

    @Override
    protected void populateWorld() {
        createTitle();
        createPlayButton();
        createScoreButton();
        createQuitButton();
        createSettingsButton();
        createSettingsDock();
        createSettingsOverlay();
        showOverlaySystem.hideOverlay();
    }

    private void createTitle() {
        Texture titleTexture = gameInstance.assets.manager.get("galacticTD.png", Texture.class);

        float titleX = GalacticTDGame.UI_WIDTH / 3f;
        float titleY = GalacticTDGame.UI_HEIGHT * 0.625f;
        float titleWidth = GalacticTDGame.UI_WIDTH / 3f;
        float titleHeight = GalacticTDGame.UI_HEIGHT / 3f;

        archetypeBuilder.createUiSprite(titleX, titleY, titleWidth, titleHeight, titleTexture);
    }

    private void createPlayButton() {
        Texture playButtonTexture = gameInstance.assets.manager.get("buttonPlay.png", Texture.class);

        float playButtonX = GalacticTDGame.UI_WIDTH / 3f;
        float playButtonY = GalacticTDGame.UI_HEIGHT * 0.375f;
        float playButtonWidth = GalacticTDGame.UI_WIDTH / 3f;
        float playButtonHeight = GalacticTDGame.UI_HEIGHT / 3f;

        Event playButtonEvent = new Event() {
            @Override
            public void fireEvent() {
                gameInstance.getScreenManager().setScreen(GameScreen.class);
            }
        };

        archetypeBuilder.createUiButton(playButtonX, playButtonY, playButtonWidth, playButtonHeight, playButtonTexture, playButtonEvent);
    }

    private void createScoreButton() {
        Texture scoreButtonTexture = gameInstance.assets.manager.get("buttonScore.png", Texture.class);

        float scoreButtonX = GalacticTDGame.UI_WIDTH * (1f / 24f);
        float scoreButtonY = (GalacticTDGame.UI_HEIGHT * (1f / 8f));

        Event scoreButtonEvent = new Event() {
            @Override
            public void fireEvent() {
                gameInstance.getScreenManager().setScreen(ScoreScreen.class);
            }
        };

        archetypeBuilder.createUiButton(scoreButtonX, scoreButtonY, buttonWidth, buttonHeight, scoreButtonTexture, scoreButtonEvent);
    }

    private void createQuitButton() {
        Texture quitButtonTexture = gameInstance.assets.manager.get("buttonQuit.png", Texture.class);

        float quitButtonX = (GalacticTDGame.UI_WIDTH * (5f / 12f));
        float quitButtonY = (GalacticTDGame.UI_HEIGHT / 24f);

        Event quitButtonEvent = new Event() {
            @Override
            public void fireEvent() {
                Gdx.app.exit();
            }
        };

        archetypeBuilder.createUiButton(quitButtonX, quitButtonY, buttonWidth, buttonHeight, quitButtonTexture, quitButtonEvent);
    }

    private void createSettingsButton() {
        Texture settingsButtonTexture = gameInstance.assets.manager.get("settings.png", Texture.class);

        float settingsButtonX = (GalacticTDGame.UI_WIDTH - (GalacticTDGame.UI_WIDTH / 24f) - buttonWidth);
        float settingsButtonY = (GalacticTDGame.UI_HEIGHT / 8f);

        Event settingsButtonEvent = new Event() {
            @Override
            public void fireEvent() {
                showOverlaySystem.showOverlay();
            }
        };

        archetypeBuilder.createUiButton(settingsButtonX, settingsButtonY, buttonWidth, buttonHeight, settingsButtonTexture, settingsButtonEvent);
    }

    private void createSettingsOverlay() {
        int overlay = archetypeBuilder.buildArchetype(HomeScreenArchetypeBuilder.overlayButton);

        Layer overlayLayer = layerComponentMapper.get(overlay);
        overlayLayer.layerLevel = LayerLevel.OVERLAY;

        Renderable overlayRenderable = renderableComponentMapper.get(overlay);
        overlayRenderable.texture = gameInstance.assets.manager.get("black.png", Texture.class);
        overlayRenderable.a = .65f;

        Position overlayPosition = positionComponentMapper.get(overlay);
        overlayPosition.setBounds(0, 0, GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

        Touchable overlayTouch = touchableComponentMapper.get(overlay);
        overlayTouch.event = new Event() {
            @Override
            public void fireEvent() {
                showOverlaySystem.hideOverlay();
            }
        };
    }

    private void createSettingsDock() {
        int settingsDock = archetypeBuilder.buildArchetype(HomeScreenArchetypeBuilder.dock);

        Layer dockLayer = layerComponentMapper.get(settingsDock);
        dockLayer.layerLevel = LayerLevel.OVERLAY_1;

        Renderable settingsRenderable = renderableComponentMapper.get(settingsDock);
        settingsRenderable.texture = gameInstance.assets.manager.get("border.png", Texture.class);

        Position dockPosition = positionComponentMapper.get(settingsDock);
        dockPosition.width = GalacticTDGame.UI_WIDTH / 4f;
        dockPosition.height = dockPosition.width;

        ResetPosition dockResetCords = resetPositionComponentMapper.get(settingsDock);
        dockResetCords.resetPositionX = GalacticTDGame.UI_WIDTH;
        dockResetCords.resetPositionY = 0 - dockPosition.height;

        dockPosition.x = dockResetCords.resetPositionX;
        dockPosition.y = dockResetCords.resetPositionY;

        MoveToPoint dockMoveToPoint = moveToPointComponentMapper.get(settingsDock);
        dockMoveToPoint.moving = false;

        MovementDestination movementDestination = movementDestinationComponentMapper.create(settingsDock);
        movementDestination.destinationX = GalacticTDGame.UI_WIDTH - dockPosition.width;
        movementDestination.destinationY = 0;

        MovementSpeed movementSpeed = movementSpeedComponentMapper.create(settingsDock);
        movementSpeed.unitsPerSecond = 3000f;
    }
}
