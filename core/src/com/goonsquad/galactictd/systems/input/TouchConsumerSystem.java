package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class TouchConsumerSystem extends BaseEntitySystem implements InputProcessor {
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<Position> positionComponentMapper;
    private int currentTouchedEntity;
    private Viewport viewport;
    private Vector2 touchLoc;
    private boolean justTouched;
    private ArrayList<Integer> sortedEntities;
    private Comparator<Integer> layerComparator;

    public TouchConsumerSystem(Viewport viewport, Aspect.Builder aspect) {
        super(aspect.all(Touchable.class, Position.class));
        this.viewport = viewport;
        touchLoc = new Vector2();
        this.justTouched = false;
        sortedEntities = new ArrayList<Integer>();
    }

    @Override
    protected void initialize() {
        layerComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer entityOne, Integer entityTwo) {
                Touchable touchableOne = touchableComponentMapper.get(entityOne);
                Touchable touchableTwo = touchableComponentMapper.get(entityTwo);
                return touchableTwo.layer.compareTo(touchableOne.layer);
            }
        };
    }

    @Override
    protected final void processSystem() {
        if (justTouched) {
            Touchable touchable = touchableComponentMapper.get(currentTouchedEntity);
            if (touchable.event != null) {
                touchable.event.fireEvent();
            }
        }
    }

    @Override
    public void inserted(int entityId) {
        sortedEntities.add(entityId);
        sortedEntities.sort(layerComparator);
    }

    @Override
    protected void removed(int entityId) {
        sortedEntities.remove((Integer) entityId);
    }

    @Override
    protected final void end() {
        justTouched = false;
    }

    private boolean checkIfEntitiesWereTouched(Vector2 touchLoc) {
        Position entityPosition;

        for (int id : sortedEntities) {
            entityPosition = positionComponentMapper.get(id);
            if (entityPosition.containsPoint(touchLoc.x, touchLoc.y)) {
                currentTouchedEntity = id;
                setEnabled(true);
                this.justTouched = true;
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
        if (isEnabled()) {
            touchLoc.set(screenX, screenY);
            touchLoc = viewport.unproject(touchLoc);
            return checkIfEntitiesWereTouched(touchLoc);
        }
        return false;
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
