package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.badlogic.gdx.graphics.Camera;
import com.goonsquad.galactictd.components.graphics.DrawInUi;

//This system only draws entities that have all the components that match up to
//the parent aspect and the aspects specified here.
public class UiRenderSystem extends RenderSystem {
    public UiRenderSystem(Camera camera) {
        super(camera, Aspect.all(DrawInUi.class));
    }
}
