package com.goonsquad.galactictd.components.layers;

public enum Layer {
    DEFAULT,
    BACKGROUND,
    GAME,
    UI,
    OVERLAY,
    OVERLAY_1;

    public int getLayerId(){
        return ordinal();
    }
}
