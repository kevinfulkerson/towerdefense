package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ScoreScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    public static final String ship = "ship";

    public ScoreScreenArchetypeBuilder(GalacticTDGame gameInstance) {
        super(gameInstance);
    }

    @Override
    protected void createCustomArchetypes() {
        super.addArchetypeToSystem(ship, ArchetypeBuilderSystem.uiLabel, ResetPosition.class, MoveToPoint.class);
    }
}
