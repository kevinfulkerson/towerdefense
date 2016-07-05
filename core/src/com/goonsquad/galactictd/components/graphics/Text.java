package com.goonsquad.galactictd.components.graphics;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Text extends com.artemis.PooledComponent {
    public BitmapFont font;
    public String text;

    @Override
    protected void reset() {
        font = null;
        text = null;
    }
}
