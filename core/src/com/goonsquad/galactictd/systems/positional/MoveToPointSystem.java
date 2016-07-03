package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class MoveToPointSystem extends IteratingSystem {
    ComponentMapper<Position> positionComponentMapper;
    ComponentMapper<MovementDestination> destinationComponentMapper;
    ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    ComponentMapper<ResetPosition> resetPositionComponentMapper;

    private Position currentPosition;
    private MovementDestination movementDestination1;
    private MovementSpeed movementSpeed;
    private MoveToPoint moveToPoint;

    public MoveToPointSystem() {
        super(Aspect.all(Position.class, MovementDestination.class, MovementSpeed.class, MoveToPoint.class));
    }

    //TODO
    //Make this do less operations.
    @Override
    protected void process(int entityId) {
        moveToPoint = moveToPointComponentMapper.get(entityId);
        if (moveToPoint.moving) {
            currentPosition = positionComponentMapper.get(entityId);
            movementDestination1 = destinationComponentMapper.get(entityId);
            movementSpeed = movementSpeedComponentMapper.get(entityId);

            float distanceToDestination =
                    (float) Math.sqrt(Vector2.dst2(currentPosition.x, currentPosition.y, movementDestination1.destinationX, movementDestination1.destinationY));

            float travelDistance = movementSpeed.unitsPerSecond * world.getDelta();

            float progress = travelDistance / distanceToDestination;

            progress = MathUtils.clamp(progress, 0, 1);

            currentPosition.x = MathUtils.lerp(currentPosition.x, movementDestination1.destinationX, progress);
            currentPosition.y = MathUtils.lerp(currentPosition.y, movementDestination1.destinationY, progress);

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
