package com.goonsquad.galactictd.components.positional;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class Rotatable extends com.artemis.PooledComponent {

    public boolean rotating;
    public boolean continuousRotation;
    public float rotationInRadians;
    public float progress;
    public float rotationTargetAngle;
    public Vector2 rotationTargetPoint;
    public float rotationTargetAmount;

    @Override
    protected void reset() {
        rotating = false;
        continuousRotation = false;
        rotationInRadians = 0f;
        progress = 0f;
        rotationTargetAngle = 0f;
        rotationTargetPoint = null;
        rotationTargetAmount = 0f;
    }

    public void prepareOneTimeRotation(float targetAngle) {
        rotationTargetAngle = targetAngle;
        rotationTargetPoint = null;
        this.prepareOneTimeRotation();
    }

    public void prepareOneTimeRotation(Vector2 targetPoint) {
        rotationTargetAngle = 0f;
        rotationTargetPoint = targetPoint;
        this.prepareOneTimeRotation();
    }

    private void prepareOneTimeRotation() {
        continuousRotation = false;
        progress = 0f;
        rotationTargetAmount = 0f;
        rotating = true;
    }

    public void prepareContinuousRotation() {
        continuousRotation = true;
        rotating = true;
    }

    public float getRotationInDegrees() {
        return rotationInRadians * 180f / MathUtils.PI;
    }
}
