package com.goonsquad.galactictd.components.input;

public class Touchable extends com.artemis.PooledComponent {
    public Event event;

    @Override
    protected void reset() {
        event = null;
    }
}
