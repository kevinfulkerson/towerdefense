package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class ScoreScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    @Override
    protected void createCustomArchetypes() {
        super.addArchetypeToSystem("ship", "ui_label", ResetPosition.class, MoveToPoint.class);
    }
}
