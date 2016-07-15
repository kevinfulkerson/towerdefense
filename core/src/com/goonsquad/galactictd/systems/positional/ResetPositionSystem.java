package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.positional.Spacial;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ResetPositionSystem extends com.artemis.systems.IteratingSystem {
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Spacial> positionComponentMapper;

    public ResetPositionSystem() {
        super(Aspect.all(ResetPosition.class, Spacial.class));
    }

    @Override
    protected void process(int entityId) {
        ResetPosition resetPosition = resetPositionComponentMapper.get(entityId);
        if (resetPosition.positionNeedsReset) {
            Spacial spacial = positionComponentMapper.get(entityId);
            spacial.x = resetPosition.resetPositionX;
            spacial.y = resetPosition.resetPositionY;
            resetPosition.positionNeedsReset = false;
        }
    }
}
