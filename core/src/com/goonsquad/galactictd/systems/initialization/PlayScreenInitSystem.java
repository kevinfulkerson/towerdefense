package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.positional.BoundsType;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.RotationSpeed;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.screens.PlayScreen;
import com.goonsquad.galactictd.systems.archetypes.ArchetypeBuilderSystem;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.input.ContextTouchSystem;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;

public class PlayScreenInitSystem extends InitializationSystem {

    private PlayScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Spatial> spatialComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<MovementDestination> movementDestinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<RotationSpeed> rotationSpeedComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;
    private MoveToPointSystem moveToPointSystem;
    private ContextTouchSystem contextTouchSystem;

    private GalacticTDGame gameInstance;
    private Texture shipTexture;
    private final float shipRadius = 100f;

    public PlayScreenInitSystem(GalacticTDGame game) {
        this.gameInstance = game;
    }

    @Override
    protected void populateWorld() {
        shipTexture = gameInstance.assets.manager.get("ship.png", Texture.class);
        createTopUiBar();
        createPlayer();
        createHomeBase();
        createShips(); // TODO: consider making this tied to some "game rules" object
        createOverlays();
    }

    private int createTopUiBar() {
        //Ui example
        int uiBar = archetypeBuilder.buildArchetype(ArchetypeBuilderSystem.uiButton);
        Spatial uiBarSpatial = spatialComponentMapper.get(uiBar);

        uiBarSpatial.width = GalacticTDGame.UI_WIDTH;
        uiBarSpatial.height = PlayScreen.HUD_HEIGHT;
        uiBarSpatial.x = 0;
        uiBarSpatial.y = GalacticTDGame.UI_HEIGHT - uiBarSpatial.height;
        return uiBar;
    }

    private void createPlayer() {
        // TODO: create player stuff for tracking upgrades, life, etc
    }

    private void createHomeBase() {
        int homeBaseId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.HOME_BASE_SPRITE);

        Renderable homeBaseRenderable = renderableComponentMapper.get(homeBaseId);
        homeBaseRenderable.texture = gameInstance.assets.manager.get("Owens_Frank.jpg", Texture.class);

        Spatial homeBasePosition = spatialComponentMapper.get(homeBaseId);
        homeBasePosition.spatialType = BoundsType.Circle;
        homeBasePosition.radius = 100f;
        homeBasePosition.centerX = PlayScreen.GAME_WIDTH * 0.50f;
        homeBasePosition.centerY = (PlayScreen.GAME_HEIGHT) * 0.50f;

        Touchable shipTouchable = touchableComponentMapper.get(homeBaseId);
        shipTouchable.event = new Event() {
            @Override
            public void fireEvent() {
                // For now, consider this as just cancelling the movement
                Gdx.app.log("Home Base", "Touched");
            }
        };
    }

    private void createShips() {
        createShip(Color.BLUE, PlayScreen.GAME_WIDTH * 0.50f, PlayScreen.GAME_HEIGHT * 0.80f);
        createShip(Color.GREEN, PlayScreen.GAME_WIDTH * 0.75f, PlayScreen.GAME_HEIGHT * 0.30f);
        createShip(Color.RED, PlayScreen.GAME_WIDTH * 0.25f, PlayScreen.GAME_HEIGHT * 0.30f);
    }

    private void createShip(final Color color, float centerX, float centerY) {
        final int shipId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.SHIP_SPRITE);

        Renderable shipRenderable = renderableComponentMapper.get(shipId);
        shipRenderable.texture = shipTexture;
        shipRenderable.r = color.r;
        shipRenderable.g = color.g;
        shipRenderable.b = color.b;

        Spatial shipSpacial = spatialComponentMapper.get(shipId);
        shipSpacial.spatialType = BoundsType.Circle;
        shipSpacial.radius = shipRadius;
        shipSpacial.centerX = centerX;
        shipSpacial.centerY = centerY;

        Touchable shipTouchable = touchableComponentMapper.get(shipId);
        shipTouchable.event = new Event() {
            @Override
            public void fireEvent() {
                // Add this entity to the user-initiated movement system
                Gdx.app.log("Ship " + color.toString(), "Touched");
                contextTouchSystem.openContextForShipMovement(shipId);
            }
        };
    }


    private void createOverlays() {
        // TODO: create the information overlays
    }
}
