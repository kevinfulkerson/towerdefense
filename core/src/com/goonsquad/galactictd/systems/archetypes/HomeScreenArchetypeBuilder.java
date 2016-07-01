package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class HomeScreenArchetypeBuilder extends ArchetypeBuilderSystem {

    @Override
    protected void createCustomArchetypes() {
        this.addArchetypeToSystem("dock", "overlay_button", ResetPosition.class, MoveToPoint.class);
    }
}
