package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.graphics.DrawInOverlay;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class HomeScreenArchetypeBuilder extends ArchetypeBuilderSystem {

    @Override
    protected void createCustomArchetypes() {
        this.addArchetypeToSystem("overlay_sprite", "sprite", DrawInOverlay.class);
        this.addArchetypeToSystem("overlay_button", "overlay_sprite", Touchable.class);
        this.addArchetypeToSystem("dock", "overlay_button", ResetPosition.class, MoveToPoint.class);
    }
}
