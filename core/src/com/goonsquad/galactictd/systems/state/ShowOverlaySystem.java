package com.goonsquad.galactictd.systems.state;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.graphics.DrawInOverlay;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;

//This system is used to manage what happens to entities when the overlay is hidden/shown.
public class ShowOverlaySystem extends com.artemis.systems.IteratingSystem {
    private ComponentMapper<DrawInUi> uiComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;

    private boolean show;

    public ShowOverlaySystem() {
        super(Aspect.all(DrawInOverlay.class));
        this.setEnabled(false);
        show = false;
    }

    @Override
    protected void process(int entityId) {
        if (show) {
            showEntity(entityId);
        } else {
            hideEntity(entityId);
        }
    }

    private void showEntity(int entityId) {
        uiComponentMapper.set(entityId, true);
        if (moveToPointComponentMapper.has(entityId)) {
            moveToPointComponentMapper.get(entityId).moving = true;
        }
        if (touchableComponentMapper.has(entityId))
            touchableComponentMapper.get(entityId).acceptingTouch = true;
    }

    private void hideEntity(int entityId) {
        uiComponentMapper.set(entityId, false);
        if (resetPositionComponentMapper.has(entityId)) {
            resetPositionComponentMapper.get(entityId).positionNeedsReset = true;
        }
        if (moveToPointComponentMapper.has(entityId)) {
            moveToPointComponentMapper.get(entityId).moving = false;
        }
        if (touchableComponentMapper.has(entityId))
            touchableComponentMapper.get(entityId).acceptingTouch = false;
    }

    @Override
    protected void end() {
        this.setEnabled(false);
    }

    public void showOverlay() {
        this.setEnabled(true);
        this.show = true;
    }

    public void hideOverlay() {
        this.setEnabled(true);
        this.show = false;
    }
}
