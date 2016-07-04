package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

public class UiTouchSystem extends TouchConsumerSystem {

    public UiTouchSystem(Viewport uiViewport) {
        super(uiViewport, Aspect.all(DrawInUi.class));
    }
}
