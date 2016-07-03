package com.goonsquad.galactictd.components.layers;

public class Layer extends com.artemis.PooledComponent {
    public LayerLevel layerLevel = LayerLevel.DEFAULT;

    @Override
    protected void reset() {
        this.layerLevel = LayerLevel.DEFAULT;
    }


}