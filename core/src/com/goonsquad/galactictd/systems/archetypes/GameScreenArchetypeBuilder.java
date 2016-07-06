package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.positional.Position;

public class GameScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private static String gameSprite = "game_sprite";

    @Override
    protected void createCustomArchetypes() {
        addArchetypeToSystem(gameSprite, GameScreenArchetypeBuilder.sprite, DrawInGame.class);
    }

    public int createGameSprite(float x, float y, float width, float height, Texture image) {
        int sprite = buildArchetype(gameSprite);

        Position spritePosition = positionComponentMapper.get(sprite);
        spritePosition.setBounds(x, y, width, height);

        Renderable spriteRenderable = renderableComponentMapper.get(sprite);
        spriteRenderable.texture = image;

        return sprite;
    }
}
