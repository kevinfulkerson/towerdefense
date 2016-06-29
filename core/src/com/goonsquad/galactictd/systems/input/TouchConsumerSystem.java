package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

public abstract class TouchConsumerSystem extends BaseEntitySystem implements InputProcessor {
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<Position> positionComponentMapper;
    private int currentTouchedEntity;
    private Viewport viewport;
    private Vector2 touchLoc;

    public TouchConsumerSystem(Viewport viewport, Aspect.Builder aspect) {
        super(aspect.all(Touchable.class, Position.class));
        this.viewport = viewport;
        touchLoc = new Vector2();
        this.setEnabled(false);
        Gdx.app.log("TouchConsumerSystem", "initialized");
    }

    @Override
    protected final void processSystem() {
        Touchable touchable = touchableComponentMapper.get(currentTouchedEntity);
        touchable.event.fireEvent();
    }

    @Override
    protected final void end() {
        setEnabled(false);
    }

    private boolean checkIfEntitiesWereTouched(Vector2 touchLoc) {
        Position entityPosition;

        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();

        for (int i = 0, s = actives.size(); s > i; i++) {
            entityPosition = positionComponentMapper.get(ids[i]);
            if (entityPosition.containsPoint(touchLoc.x, touchLoc.y)) {
                currentTouchedEntity = ids[i];
                setEnabled(true);
                return true;
            }
        }

        return false;
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
        Gdx.app.log("TouchSystem", "touch heard.");
        touchLoc.set(screenX, screenY);
        touchLoc = viewport.unproject(touchLoc);
        return checkIfEntitiesWereTouched(touchLoc);
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
