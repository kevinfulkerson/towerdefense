package com.goonsquad.galactictd.components.positional;

public class MovementSpeed extends com.artemis.PooledComponent {
    public float unitsPerSecond;

    @Override
    protected void reset() {
        unitsPerSecond = 0;
    }
}
