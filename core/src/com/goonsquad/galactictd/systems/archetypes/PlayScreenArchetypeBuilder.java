package com.goonsquad.galactictd.systems.archetypes;

import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.input.MovementContext;
import com.goonsquad.galactictd.components.input.Touchable;

public class PlayScreenArchetypeBuilder extends ArchetypeBuilderSystem {

    public static final String SHIP_SPRITE = "ship_sprite";
    public static final String HOME_BASE_SPRITE = "home_base_sprite";
    public static final String SPIRAL_SPRITE = "spiral_sprite";
    public static final String SPAWNLING_SPRITE = "spawnling_sprite";
    public static final String CONTEXT_TOUCH_FIELD = "context_touch_field";
    public static final String CONTEXT_MENU_ITEM = "context_menu_item";

    @Override
    protected void createCustomArchetypes() {
        // TODO: fill in this section appropriately
        this.addArchetypeToSystem(SHIP_SPRITE, ArchetypeBuilderSystem.sprite, DrawInUi.class, Touchable.class);
        this.addArchetypeToSystem(HOME_BASE_SPRITE, ArchetypeBuilderSystem.sprite, DrawInUi.class, Touchable.class);
        this.addArchetypeToSystem(SPIRAL_SPRITE, ArchetypeBuilderSystem.sprite, DrawInUi.class, Touchable.class);
        this.addArchetypeToSystem(SPAWNLING_SPRITE, ArchetypeBuilderSystem.sprite, DrawInUi.class, Touchable.class);
        this.addArchetypeToSystem(CONTEXT_TOUCH_FIELD, ArchetypeBuilderSystem.sprite, Touchable.class, MovementContext.class);
        this.addArchetypeToSystem(CONTEXT_MENU_ITEM, ArchetypeBuilderSystem.sprite, DrawInUi.class, Touchable.class, MovementContext.class);
    }
}
