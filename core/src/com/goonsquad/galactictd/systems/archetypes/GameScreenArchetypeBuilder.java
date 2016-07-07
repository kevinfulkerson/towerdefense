package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

public class GameScreenArchetypeBuilder extends ArchetypeBuilderSystem {
    private ComponentMapper<Touchable> touchableComponentMapper;

    @Override
    protected void createCustomArchetypes() {
    }

    public int createGameSprite(float x, float y, float width, float height, Texture image) {
        int sprite = buildArchetype(gameSprite);
        editSprite(sprite, x, y, width, height, image);
        return sprite;
    }

    public int createGameButton(float x, float y, float width, float height, Texture image, Event event) {
        int button = buildArchetype(gameButton);
        editSprite(button, x, y, width, height, image);

        Touchable buttonTouch = touchableComponentMapper.get(button);
        buttonTouch.event = event;

        return button;
    }
}
