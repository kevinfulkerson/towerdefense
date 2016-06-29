package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.StartingCords;

public class MoveToPointSystem extends IteratingSystem {
    ComponentMapper<Position> positionComponentMapper;
    ComponentMapper<StartingCords> startingCordsComponentMapper;
    ComponentMapper<MoveToPoint> locationComponentMapper;

    private Position currentPosition;
    private StartingCords startingCords;
    private MoveToPoint location;

    public MoveToPointSystem() {
        super(Aspect.all(Position.class, MoveToPoint.class));
    }

    @Override
    protected void process(int entityId) {
        currentPosition = positionComponentMapper.get(entityId);
        location = locationComponentMapper.get(entityId);
        startingCords = startingCordsComponentMapper.get(entityId);

        float distanceToDestination =
                (float) Math.sqrt(Vector2.dst2(currentPosition.x, currentPosition.y, location.destinationX, location.destinationY));

        float travelDistance = location.speedPerSecond * world.getDelta();

        float progress = travelDistance / distanceToDestination;

        progress = MathUtils.clamp(progress, 0, 1);

        currentPosition.x = MathUtils.lerp(currentPosition.x, location.destinationX, progress);
        currentPosition.y = MathUtils.lerp(currentPosition.y, location.destinationY, progress);

        if (location.rotateTowardsPoint) {
            float xDiff = location.destinationX - currentPosition.x;
            double yDiff = location.destinationY - currentPosition.y;
            currentPosition.rotation = (float) Math.toDegrees(Math.atan2(yDiff, xDiff));
        }

        if (progress >= 1) {
            if (location.returnToStartingCords) {
                currentPosition.x = startingCords.x;
                currentPosition.y = startingCords.y;
            } else {
                locationComponentMapper.remove(entityId);
            }
        }
    }
}
