package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.Camera;
import com.goonsquad.galactictd.components.graphics.DrawInGame;

public class GameRenderSystem extends RenderSystem {
    public GameRenderSystem(Camera camera) {
        super(camera, Aspect.all(DrawInGame.class));
    }
}
