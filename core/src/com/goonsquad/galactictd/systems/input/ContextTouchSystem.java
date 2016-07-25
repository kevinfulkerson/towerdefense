package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.input.MovementContext;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.layers.LayerLevel;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.RotationSpeed;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;

public class ContextTouchSystem extends TouchConsumerSystem {

    private static final int INVALID_ENTITY = -1;

    private ComponentMapper<MovementDestination> destinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<RotationSpeed> rotationSpeedComponentMapper;

    private PlayScreenArchetypeBuilder archetypeBuilder;
    private MovementDestination currentMovementDestination;
    private MovementSpeed currentMovementSpeed;
    private MoveToPoint currentMoveToPoint;

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
        if (currentEntityId != INVALID_ENTITY) {
            super.touchLoc.set(screenX, screenY);
            super.touchLoc = viewport.unproject(super.touchLoc);
            currentMovementDestination = destinationComponentMapper.create(currentEntityId);
            currentMovementDestination.destinationX = super.touchLoc.x;
            currentMovementDestination.destinationY = super.touchLoc.y;
            currentMovementSpeed = movementSpeedComponentMapper.create(currentEntityId);
            currentMovementSpeed.unitsPerSecond = 500;
            currentMoveToPoint = moveToPointComponentMapper.create(currentEntityId);
            currentMoveToPoint.moving = true;
            currentMoveToPoint.resetPositionOnArrival = false;

            if(rotatableComponentMapper.has(currentEntityId)) {
                Rotatable rotate = rotatableComponentMapper.get(currentEntityId);
                rotate.prepareOneTimeRotation(touchLoc);
            }

            if(rotationSpeedComponentMapper.has(currentEntityId)) {
                RotationSpeed rotationSpeed = rotationSpeedComponentMapper.get(currentEntityId);
                rotationSpeed.radiansPerSecond = RotationSpeed.VERY_FAST_ROTATION;
            }

            // Reset the touch handler
            currentEntityId = INVALID_ENTITY;

            return true;
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
