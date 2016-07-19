package com.goonsquad.galactictd.components.positional;

import com.badlogic.gdx.math.Vector2;

public class Spatial extends com.artemis.PooledComponent {

    public float x = 0;
    public float y = 0;
    public float centerX = 0;
    public float centerY = 0;
    public float radius = 0;
    public float width = 0;
    public float height = 0;
    public float rotation = 0;
    public BoundsType spatialType = BoundsType.Rectangle;

    @Override
    protected void reset() {
        x = 0;
        y = 0;
        centerX = 0;
        centerY = 0;
        radius = 0;
        width = 0;
        height = 0;
        rotation = 0;
        spatialType = null;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean containsPoint(float x, float y) {
        if (this.spatialType == BoundsType.Rectangle)
            return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
        else if (this.spatialType == BoundsType.Circle) {
            return Vector2.dst(centerX, centerY, x, y) <= this.radius;
        }
        return false;
    }

    public void setOriginX(float x) {
        this.x = x - width / 2f;
    }

    public void setOriginY(float y) {
        this.y = y - height / 2f;
    }

    public float getOriginX() {
        return x + width / 2f;
    }

    public float getOriginY() {
        return y + height / 2f;
    }
}
