package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.Camera;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.graphics.Renderable;

public class UiRenderSystem extends RenderSystem {
    public UiRenderSystem(Camera camera) {
        super(camera, Aspect.all(Renderable.class, DrawInUi.class, Position.class));
    }

    @Override
    protected void process(int entityId) {
        super.process(entityId);
    }
}
