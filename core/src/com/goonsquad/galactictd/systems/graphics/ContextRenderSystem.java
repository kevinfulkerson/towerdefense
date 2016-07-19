package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.components.graphics.DrawInContext;

public class ContextRenderSystem extends RenderSystem {
    public ContextRenderSystem(Camera camera, Texture missingTextureImage) {
        super(camera, Aspect.all(DrawInContext.class), missingTextureImage);
    }
}
