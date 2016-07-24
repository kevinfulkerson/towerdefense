package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.RotationSpeed;
import com.goonsquad.galactictd.components.positional.Spatial;

public class RotationSystem extends IteratingSystem {

    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<RotationSpeed> rotationSpeedComponentMapper;
    private ComponentMapper<Spatial> spatialComponentMapper;

    private Spatial spatial;
    private Rotatable rotatable;
    private RotationSpeed rotationSpeed;

    public RotationSystem() {
        super(Aspect.all(Rotatable.class, RotationSpeed.class));
    }

    @Override
    protected void process(int entityId) {
        rotatable = rotatableComponentMapper.get(entityId);
        rotationSpeed = rotationSpeedComponentMapper.get(entityId);
        if(spatialComponentMapper.has(entityId)) {
            spatial = spatialComponentMapper.get(entityId);
        } else {
            spatial = null;
        }

        if(rotatable.rotating) {
            // Check if we are set to rotate continually
            if(rotatable.continuousRotation)
            {
                // Just manually rotate ourselves some amount
                rotatable.rotationInRadians += rotationSpeed.radiansPerTick * world.getDelta();
                rotatable.rotationInRadians %= MathUtils.PI2;
            } else {
                // If we have a target point and a spatial component, use the point for the angle
                if (rotatable.rotationTargetPoint != null && spatial != null) {
                    // Retrieve the angle relative to entity current location
                    rotatable.rotationTargetAngle = MathUtils.atan2(
                            (rotatable.rotationTargetPoint.y - spatial.getOriginY()),
                            (rotatable.rotationTargetPoint.x - spatial.getOriginX()));

                    // Normalize the angle to match the environment
                    rotatable.rotationTargetAngle -= (MathUtils.PI * 0.5f);

                    // Reset target point so we don't recalculate this
                    rotatable.rotationTargetPoint = null;
                }

                // If our progress is at 0, the rotation hasn't been setup
                if(MathUtils.isZero(rotatable.progress)) {
                    // Get the normalized target angle and set that as the target angle
                    rotatable.rotationTargetAngle = MathUtils.lerpAngle(rotatable.rotationInRadians,
                            rotatable.rotationTargetAngle, 1);

                    rotatable.rotationTargetAmount =
                            ((rotatable.rotationTargetAngle - rotatable.rotationInRadians
                                    + MathUtils.PI2 + MathUtils.PI) % MathUtils.PI2) - MathUtils.PI;
                }

                // Calculate the current progress
                // TODO: optimize this mess
                rotatable.progress = (rotationSpeed.radiansPerTick * world.getDelta())
                        / Math.abs(Math.abs(rotatable.rotationTargetAmount) -
                        (rotationSpeed.radiansPerTick * world.getDelta() * rotatable.ticks++));

                // Clamp the progress to be between 0 and 1
                rotatable.progress = MathUtils.clamp(rotatable.progress, 0f, 1f);

                // Linearly interpolate the angle based on the previously calculated progress
                rotatable.rotationInRadians = MathUtils.lerpAngle(rotatable.rotationInRadians,
                        rotatable.rotationTargetAngle, rotatable.progress);

                if(MathUtils.isEqual(rotatable.progress, 1)) {
                    rotatable.rotating = false;
                }
            }
        }
    }
}
