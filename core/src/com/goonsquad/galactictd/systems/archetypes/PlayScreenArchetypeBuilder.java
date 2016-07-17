package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.game.Warp;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Spatial;

public class PlayScreenArchetypeBuilder extends ArchetypeBuilderSystem {

    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Spatial> spatialComponentMapper;
    public static final String SHIP_SPRITE = "ship_sprite";
    public static final String HOME_BASE_SPRITE = "home_base_sprite";
    public static final String SPIRAL_SPRITE = "spiral_sprite";
    public static final String SPAWNLING_SPRITE = "spawnling_sprite";
    public static final String WARP = "warp";

    public PlayScreenArchetypeBuilder(GalacticTDGame gameInstance) {
        super(gameInstance);
    }

    @Override
    protected void createCustomArchetypes() {
        // TODO: fill in this section appropriately
        this.addArchetypeToSystem(SHIP_SPRITE, ArchetypeBuilderSystem.sprite, DrawInGame.class, Touchable.class);
        this.addArchetypeToSystem(HOME_BASE_SPRITE, ArchetypeBuilderSystem.sprite, DrawInGame.class, Touchable.class);
        this.addArchetypeToSystem(SPIRAL_SPRITE, ArchetypeBuilderSystem.sprite, DrawInGame.class, Touchable.class);
        this.addArchetypeToSystem(SPAWNLING_SPRITE, ArchetypeBuilderSystem.sprite, DrawInGame.class, Touchable.class);
        this.addArchetypeToSystem(WARP, ArchetypeBuilderSystem.sprite, DrawInGame.class, Warp.class);
    }

    public int createWarp(float centerX, float centerY, float width, float height) {
        int warp = this.buildArchetype(WARP);

        Renderable warpRenderable = renderableComponentMapper.get(warp);
        warpRenderable.texture = gameInstance.assets.manager.get("warp.png", Texture.class);

        Spatial warpSpatial = spatialComponentMapper.get(warp);
        warpSpatial.setBounds(centerX - width / 2f, centerY - height / 2f, width, height);

        return 0;
    }
}
