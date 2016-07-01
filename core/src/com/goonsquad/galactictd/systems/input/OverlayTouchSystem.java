package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.graphics.DrawInOverlay;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

public class OverlayTouchSystem extends TouchConsumerSystem {
    public OverlayTouchSystem(Viewport viewport) {
        super(viewport, Aspect.all(Position.class, DrawInOverlay.class, Touchable.class));
        this.setEnabled(false);
    }
}
