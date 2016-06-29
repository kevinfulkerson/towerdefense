package com.goonsquad.galactictd.components.positional;

public class MoveToPoint extends com.artemis.PooledComponent {
    public float destinationX;
    public float destinationY;
    public float speedPerSecond;
    public boolean rotateTowardsPoint;
    public boolean returnToStartingCords;

    @Override
    protected void reset() {
        destinationX = 0;
        destinationY = 0;
        speedPerSecond = 0;
        rotateTowardsPoint = false;
    }
}
