package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.SetDestinationOnTouch;

public class MoveToTouchSystem extends IteratingSystem implements InputProcessor {
    private ComponentMapper<MoveToPoint> locationComponentMapper;
    private Viewport viewport;
    private Vector2 touchPoint;

    public MoveToTouchSystem(Viewport viewport) {
        super(Aspect.all(SetDestinationOnTouch.class));
        this.viewport = viewport;
    }

    @Override
    protected void initialize() {
        this.setEnabled(false);
    }

    @Override
    protected void process(int entityId) {
        MoveToPoint location = locationComponentMapper.create(entityId);
        location.destinationX = touchPoint.x;
        location.destinationY = touchPoint.y;
        location.speedPerSecond = 1000f;
    }

    @Override
    protected void end() {
        this.setEnabled(false);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint = new Vector2(screenX, screenY);
        this.touchPoint = viewport.unproject(touchPoint);
        this.setEnabled(true);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
