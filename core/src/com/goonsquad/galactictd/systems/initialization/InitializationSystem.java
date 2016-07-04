package com.goonsquad.galactictd.systems.initialization;

import com.artemis.BaseSystem;

public abstract class InitializationSystem extends BaseSystem {

    @Override
    protected final void initialize() {
        setEnabled(false);
        populateWorld();
    }

    protected abstract void populateWorld();

    @Override
    protected void processSystem() {

    }
}
