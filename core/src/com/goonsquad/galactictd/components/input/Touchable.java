package com.goonsquad.galactictd.components.input;

public class Touchable extends com.artemis.PooledComponent {
    public Event event;
    public boolean acceptingTouch = true;

    @Override
    protected void reset() {
        event = null;
        acceptingTouch = false;
    }
}
