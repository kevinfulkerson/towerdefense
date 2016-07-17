package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.MovementContext;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.layers.LayerLevel;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;
import com.goonsquad.galactictd.systems.positional.MoveToPointSystem;

public class ContextTouchSystem extends TouchConsumerSystem {

    private static final int INVALID_ENTITY = -1;

    private ComponentMapper<MovementDestination> destinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;

    private PlayScreenArchetypeBuilder archetypeBuilder;

    private int currentEntityId;

    public ContextTouchSystem(Viewport viewport) {
        super(viewport, Aspect.all(MovementContext.class));
        this.currentEntityId = INVALID_ENTITY;
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.addInitialEntities();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(currentEntityId != INVALID_ENTITY) {
            Vector2 location = new Vector2();
            location.set(screenX, screenY);
            location = viewport.unproject(location);
            MovementDestination dest = destinationComponentMapper.create(currentEntityId);
            dest.destinationX = location.x;
            dest.destinationY = location.y;
            MovementSpeed speed = movementSpeedComponentMapper.create(currentEntityId);
            speed.unitsPerSecond = 500;
            MoveToPoint point = moveToPointComponentMapper.create(currentEntityId);
            point.moving = true;
            point.resetPositionOnArrival = false;

            // Reset the touch handler
            currentEntityId = INVALID_ENTITY;
        }
        return false;
    }

    protected void addInitialEntities() {
        // --- Context Touch Field ---
        int contextFieldId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.CONTEXT_TOUCH_FIELD);
        Spatial contextFieldSpatial = super.spatialComponentMapper.get(contextFieldId);
        contextFieldSpatial.setBounds(0, 0, GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

        // Place this entity on the third-highest level
        Layer contextFieldLayer = super.layerComponentMapper.get(contextFieldId);
        contextFieldLayer.layerLevel = LayerLevel.UI;

//        // --- Context Error Field Menu Item ---
//        int contextErrorFieldId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.CONTEXT_MENU_ITEM);
//        Spatial contextErrorFieldSpatial = super.spatialComponentMapper.get(contextFieldId);


    }

    public void openContextForShipMovement(int shipId) {
        currentEntityId = shipId;
    }
}
