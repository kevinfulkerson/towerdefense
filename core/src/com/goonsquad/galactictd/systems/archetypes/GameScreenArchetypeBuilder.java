package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

public class GameScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;

    @Override
    protected void createCustomArchetypes() {
    }
}
