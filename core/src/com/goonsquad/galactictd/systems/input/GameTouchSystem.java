package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.graphics.DrawInGame;

public class GameTouchSystem extends TouchConsumerSystem {
    public GameTouchSystem(Viewport viewport) {
        super(viewport, Aspect.all(DrawInGame.class));
    }
}
