package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.Camera;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.graphics.Renderable;

public class GameRenderSystem extends RenderSystem {
    public GameRenderSystem(Camera camera) {
        super(camera, Aspect.all(Renderable.class, DrawInGame.class, Position.class));
    }
}
