package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.graphics.DrawInOverlay;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

public class HomeScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    public static final String overlaySprite = "overlay_sprite";
    public static final String overlayButton = "overlay_button";
    public static final String dock = "dock";

    @Override
    protected void createCustomArchetypes() {
        this.addArchetypeToSystem(overlaySprite, ArchetypeBuilderSystem.sprite, DrawInOverlay.class);
        this.addArchetypeToSystem(overlayButton, overlaySprite, Touchable.class);
        this.addArchetypeToSystem(dock, overlayButton, ResetPosition.class, MoveToPoint.class);
    }
}
