package com.goonsquad.galactictd.components.positional;

public class MoveToPoint extends com.artemis.PooledComponent {
    public boolean resetPositionOnArrival;
    public boolean moving;

    @Override
    protected void reset() {
        resetPositionOnArrival = false;
        moving = false;
    }
}
