package com.goonsquad.galactictd.components.positional;

public class Position extends com.artemis.PooledComponent {

    public float x;
    public float y;
    public float width;
    public float height;
    public float rotation;

    @Override
    protected void reset() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        rotation = 0;
    }

    public float getOriginX() {
        return x + width / 2f;
    }

    public float getOriginY() {
        return y + height / 2f;
    }


}
