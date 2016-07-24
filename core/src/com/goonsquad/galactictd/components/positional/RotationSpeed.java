package com.goonsquad.galactictd.components.positional;

public class RotationSpeed extends com.artemis.PooledComponent {

    public static final float VERY_SLOW_ROTATION = 0.5f;
    public static final float SLOW_ROTATION = 1.0f;
    public static final float MODERATE_ROTATION = 1.5f;
    public static final float FAST_ROTATION = 2.0f;
    public static final float VERY_FAST_ROTATION = 2.5f;

    // Continuous rotation will go counter-clockwise with
    // positive values, and clockwise with negative values.
    // Non-continuous rotation will calculate the shortest
    // path and follow that.
    public float radiansPerSecond;

    @Override
    protected void reset() {
        radiansPerSecond = 0f;
    }
}
