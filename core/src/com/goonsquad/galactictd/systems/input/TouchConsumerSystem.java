package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.utils.SortedEntityComponentArray;

import java.util.Comparator;

//Input events are passed to systems right before their world starts processing.
//First this system checks if any of its entities were touched.
//If so the system enables itself and runs the touched entity's event.
public abstract class TouchConsumerSystem extends BaseEntitySystem implements InputProcessor {
    protected ComponentMapper<Touchable> touchableComponentMapper;
    protected ComponentMapper<Spatial> spatialComponentMapper;
    protected ComponentMapper<Layer> layerComponentMapper;
    protected SortedEntityComponentArray<Layer> sortedEs;
    protected Comparator<Layer> layerComp;
    protected int currentTouchedEntity;
    protected Viewport viewport;
    protected Vector2 touchLoc;
    protected boolean justTouched;

    public TouchConsumerSystem(Viewport viewport, Aspect.Builder aspect) {
        super(aspect.all(Touchable.class, Spatial.class, Layer.class));
        this.viewport = viewport;
        touchLoc = new Vector2();
        this.justTouched = false;
    }

    @Override
    protected void initialize() {
        layerComp = new Comparator<Layer>() {
            @Override
            public int compare(Layer layerOne, Layer layerTwo) {
                return layerTwo.layerLevel.compareTo(layerOne.layerLevel);
            }
        };
        sortedEs = new SortedEntityComponentArray<Layer>(layerComp, layerComponentMapper);
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
        sortedEs.insert(entityId);
    }

    @Override
    protected void removed(int entityId) {
        sortedEs.remove(entityId);
    }

    @Override
    protected final void end() {
        justTouched = false;
    }

    protected boolean checkIfEntitiesWereTouched(Vector2 touchLoc) {
        Spatial entitySpatial;
        Touchable entityTouch;

        for (int id : sortedEs) {
            entityTouch = touchableComponentMapper.get(id);
            if (entityTouch.acceptingTouch) {
                entitySpatial = spatialComponentMapper.get(id);
                if (entitySpatial.containsPoint(touchLoc.x, touchLoc.y)) {
                    currentTouchedEntity = id;
                    this.justTouched = true;
                    return true;
                }
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
