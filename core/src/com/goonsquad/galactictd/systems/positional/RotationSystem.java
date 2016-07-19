package com.goonsquad.galactictd.systems.positional;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.RotationSpeed;

public class RotationSystem extends IteratingSystem {

    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<RotationSpeed> rotationSpeedComponentMapper;

    private Rotatable rotatable;
    private RotationSpeed rotationSpeed;

    public RotationSystem() {
        super(Aspect.all(Rotatable.class, RotationSpeed.class));
    }

    @Override
    protected void process(int entityId) {
        rotatable = rotatableComponentMapper.get(entityId);
        rotationSpeed = rotationSpeedComponentMapper.get(entityId);

        if(rotatable.rotating) {
            // Check if we are set to rotate continually
            if(rotatable.continuousRotation)
            {
                // Just manually rotate ourselves some amount
                rotatable.rotationInRadians += rotationSpeed.radiansPerTick * world.getDelta();
                rotatable.rotationInRadians %= MathUtils.PI2;
            } else {
                // If our progress is at 0, the rotation hasn't been setup
                if(MathUtils.isZero(rotatable.progress)) {
                    // Get the normalized target angle and set that as the target angle
                    rotatable.rotationTargetAngle = MathUtils.lerpAngle(rotatable.rotationInRadians,
                            rotatable.rotationTargetAngle, 1);
                }

                // Calculate the current progress
                rotatable.progress = (rotationSpeed.radiansPerTick * world.getDelta())
                        / Math.abs(rotatable.rotationTargetAngle - rotatable.rotationInRadians);

                // Clamp the progress to be between 0 and 1
                rotatable.progress = MathUtils.clamp(rotatable.progress, 0f, 1f);

                // Linearly interpolate the angle based on the previously calculated progress
                rotatable.rotationInRadians = MathUtils.lerpAngle(rotatable.rotationInRadians,
                        rotatable.rotationTargetAngle, rotatable.progress);

                // If we are done, reset the rotation state
                if(MathUtils.isEqual(rotatable.progress, 1)) {
                    rotatable.rotating = false;
                    rotatable.progress = 0;
                }
            }
        }
    }
}
