package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.components.positional.Spatial;

public class MoveToPointSystem extends IteratingSystem {
    private ComponentMapper<Spatial> spatialComponentMapper;
    private ComponentMapper<MovementDestination> destinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;

    private Spatial currentSpatial;
    private MovementDestination movementDestination1;
    private MovementSpeed movementSpeed;
    private MoveToPoint moveToPoint;

    private float distanceToDestination;
    private float travelDistance;
    private float progress;

    public MoveToPointSystem() {
        super(Aspect.all(Spatial.class, MovementDestination.class, MovementSpeed.class, MoveToPoint.class));
    }

    //TODO Make this do less operations.
    @Override
    protected void process(int entityId) {
        moveToPoint = moveToPointComponentMapper.get(entityId);
        if (moveToPoint.moving) {
            currentSpatial = spatialComponentMapper.get(entityId);
            movementDestination1 = destinationComponentMapper.get(entityId);
            movementSpeed = movementSpeedComponentMapper.get(entityId);

            distanceToDestination =
                    (float) Math.sqrt(Vector2.dst2(currentSpatial.getOriginX(), currentSpatial.getOriginY(), movementDestination1.destinationX, movementDestination1.destinationY));

            travelDistance = movementSpeed.unitsPerSecond * world.getDelta();

            progress = travelDistance / distanceToDestination;

            progress = MathUtils.clamp(progress, 0, 1);

            currentSpatial.setOriginX(MathUtils.lerp(currentSpatial.getOriginX(), movementDestination1.destinationX, progress));
            currentSpatial.setOriginY(MathUtils.lerp(currentSpatial.getOriginY(), movementDestination1.destinationY, progress));

            if (progress >= 1) {
                if (moveToPoint.resetPositionOnArrival) {
                    resetPositionComponentMapper.get(entityId).positionNeedsReset = true;
                } else {
                    moveToPoint.moving = false;
                }
            }
        }
    }
}
