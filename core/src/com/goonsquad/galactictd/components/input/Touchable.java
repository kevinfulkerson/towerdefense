package com.goonsquad.galactictd.components.input;

import com.goonsquad.galactictd.components.layers.Layer;

public class Touchable extends com.artemis.PooledComponent {
    public Event event;

    @Override
    protected void reset() {
        event = null;
    }
}
