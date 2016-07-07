package com.goonsquad.galactictd.systems.graphics.camera;

import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CameraManipulationSystem extends BaseSystem implements GestureDetector.GestureListener {
    private OrthographicCamera camera;
    private FitViewport viewport;
    private final float maxZoomIn;
    private final float maxZoomOut;
    private float translateX;
    private float translateY;
    private float adjustedCameraWidth;
    private float adjustedCameraHeight;

    public CameraManipulationSystem(OrthographicCamera camera, FitViewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
        maxZoomIn = .25f;
        maxZoomOut = 1.0f;
        adjustedCameraWidth = camera.viewportWidth * camera.zoom;
        adjustedCameraHeight = camera.viewportHeight * camera.zoom;
    }

    @Override
    protected void initialize() {
        this.setEnabled(false);
    }

    private void updateZoom(float newZoom) {
        camera.zoom = MathUtils.clamp(camera.zoom + newZoom, maxZoomIn, maxZoomOut);
        adjustedCameraWidth = camera.viewportWidth * camera.zoom;
        adjustedCameraHeight = camera.viewportHeight * camera.zoom;
        updateCamera();
    }

    private void updateCamera() {
        camera.position.x = MathUtils.clamp(camera.position.x, adjustedCameraWidth * .5f, camera.viewportWidth - (adjustedCameraWidth * .5f));
        camera.position.y = MathUtils.clamp(camera.position.y, adjustedCameraHeight * .5f, camera.viewportHeight - (adjustedCameraHeight * .5f));
        this.camera.update();
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

    //TODO Adjust movement distance by current viewport.screenWidth & viewport.screenHeight;
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        translateX = MathUtils.clamp(-deltaX, -20f, 20f);
        translateY = MathUtils.clamp(deltaY, -20f, 20f);
        camera.translate(translateX, translateY);
        updateCamera();
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (initialDistance - distance < 0) {
            updateZoom(-0.01f);
        } else {
            updateZoom(0.01f);
        }
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    protected void processSystem() {

    }
}
