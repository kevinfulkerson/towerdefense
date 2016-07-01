package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.goonsquad.galactictd.components.graphics.DrawInOverlay;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.systems.input.OverlayTouchSystem;

public class ShowOverlaySystem extends com.artemis.systems.IteratingSystem {
    private OverlayTouchSystem overlayTouchSystem;
    private ComponentMapper<DrawInUi> uiComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;

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
            overlayTouchSystem.setEnabled(true);
        } else {
            hideEntity(entityId);
            overlayTouchSystem.setEnabled(false);
        }
    }

    private void showEntity(int entityId) {
        uiComponentMapper.set(entityId, true);
        if (moveToPointComponentMapper.has(entityId)) {
            moveToPointComponentMapper.get(entityId).moving = true;
        }
    }

    private void hideEntity(int entityId) {
        uiComponentMapper.set(entityId, false);
        if (resetPositionComponentMapper.has(entityId)) {
            resetPositionComponentMapper.get(entityId).positionNeedsReset = true;
        }
        if (moveToPointComponentMapper.has(entityId)) {
            moveToPointComponentMapper.get(entityId).moving = false;
        }
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
