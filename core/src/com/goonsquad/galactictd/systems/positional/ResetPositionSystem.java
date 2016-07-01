package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ResetPositionSystem extends com.artemis.systems.IteratingSystem {
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Position> positionComponentMapper;

    public ResetPositionSystem() {
        super(Aspect.all(ResetPosition.class, Position.class));
    }

    @Override
    protected void process(int entityId) {
        ResetPosition resetPosition = resetPositionComponentMapper.get(entityId);
        if (resetPosition.positionNeedsReset) {
            Position position = positionComponentMapper.get(entityId);
            position.x = resetPosition.resetPositionX;
            position.y = resetPosition.resetPositionY;
            resetPosition.positionNeedsReset = false;
        }
    }
}
