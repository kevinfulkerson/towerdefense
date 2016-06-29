package com.goonsquad.galactictd.components.graphics;

import com.badlogic.gdx.graphics.Texture;

public class Renderable extends com.artemis.PooledComponent {
    public Texture texture;
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;
    public float scaleX = 1;
    public float scaleY = 1;
    public float rotation = 0;

    @Override
    protected void reset() {
        texture = null;
        r = 1;
        g = 1;
        b = 1;
        a = 1;
        scaleX = 1;
        scaleY = 1;
        rotation = 0;
    }
}