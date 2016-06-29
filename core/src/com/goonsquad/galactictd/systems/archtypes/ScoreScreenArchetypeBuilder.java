package com.goonsquad.galactictd.systems.archtypes;

import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.StartingCords;

public class ScoreScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    @Override
    protected void createCustomArchetypes() {
        super.addArchetypeToSystem("ship", "ui_label", StartingCords.class, MoveToPoint.class);
    }
}
