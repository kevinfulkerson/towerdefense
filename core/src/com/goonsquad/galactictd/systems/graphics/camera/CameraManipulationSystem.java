package com.goonsquad.galactictd.systems.graphics.camera;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CameraManipulationSystem extends BaseSystem implements GestureDetector.GestureListener {
    private OrthographicCamera camera;
    private FitViewport viewport;
    private final float maxZoomIn;
    private final float maxZoomOut;
    private final float zoomSpeed;
    private final float scrollSpeed;

    public CameraManipulationSystem(OrthographicCamera camera, FitViewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
        maxZoomIn = .25f;
        maxZoomOut = 1.0f;
        zoomSpeed = 100f;
        scrollSpeed = 100f;
    }

    @Override
    protected void processSystem() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log("Delta values", deltaX + ", " + deltaY);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
