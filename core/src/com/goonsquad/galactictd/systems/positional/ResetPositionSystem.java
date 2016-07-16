package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ResetPositionSystem extends com.artemis.systems.IteratingSystem {
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Spatial> spatialComponentMapper;

    public ResetPositionSystem() {
        super(Aspect.all(ResetPosition.class, Spatial.class));
    }

    @Override
    protected void process(int entityId) {
        ResetPosition resetPosition = resetPositionComponentMapper.get(entityId);
        if (resetPosition.positionNeedsReset) {
            Spatial spatial = spatialComponentMapper.get(entityId);
            spatial.x = resetPosition.resetPositionX;
            spatial.y = resetPosition.resetPositionY;
            resetPosition.positionNeedsReset = false;
        }
    }
}
