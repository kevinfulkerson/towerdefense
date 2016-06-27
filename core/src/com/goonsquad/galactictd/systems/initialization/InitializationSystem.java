package com.goonsquad.galactictd.systems.initialization;

import com.artemis.BaseSystem;
import com.goonsquad.galactictd.Archetypes.ArchetypeSheet;

public abstract class InitializationSystem extends BaseSystem {
    protected ArchetypeSheet sheet;

    public InitializationSystem(ArchetypeSheet sheet) {
        this.sheet = sheet;
    }

    @Override
    protected final void initialize() {
        setEnabled(false);
        initializeSheet();
        populateWorld();
    }

    protected abstract void initializeSheet();

    protected abstract void populateWorld();

    @Override
    protected final void processSystem() {
    }
}
