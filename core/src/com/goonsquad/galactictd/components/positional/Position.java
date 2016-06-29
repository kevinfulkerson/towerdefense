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

    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean containsPoint(float x, float y) {
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public float getOriginX() {
        return x + width / 2f;
    }

    public float getOriginY() {
        return y + height / 2f;
    }


}
