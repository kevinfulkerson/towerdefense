package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ResetPositionSystem extends com.artemis.systems.IteratingSystem {
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Spatial> spatialComponentMapper;

    private ResetPosition currentResetPosition;
    private Spatial currentSpatial;

    public ResetPositionSystem() {
        super(Aspect.all(ResetPosition.class, Spatial.class));
    }

    @Override
    protected void process(int entityId) {
        currentResetPosition = resetPositionComponentMapper.get(entityId);
        if (currentResetPosition.positionNeedsReset) {
            currentSpatial = spatialComponentMapper.get(entityId);
            currentSpatial.x = currentResetPosition.resetPositionX;
            currentSpatial.y = currentResetPosition.resetPositionY;
            currentResetPosition.positionNeedsReset = false;
        }
    }
}
