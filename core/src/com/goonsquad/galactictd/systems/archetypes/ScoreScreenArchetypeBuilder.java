package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ScoreScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    public static final String ship = "ship";

    @Override
    protected void createCustomArchetypes() {
        super.addArchetypeToSystem(ship, ArchetypeBuilderSystem.uiSprite, ResetPosition.class, MoveToPoint.class);
    }
}
