package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.graphics.Renderable;

public abstract class RenderSystem extends IteratingSystem {
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;

    private Position entityPosition;
    private Renderable entityRenderable;

    protected SpriteBatch batch;
    private Camera camera;

    public RenderSystem(Camera camera, Aspect.Builder aspect) {
        super(aspect);
        this.camera = camera;
        batch = new SpriteBatch();
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        entityPosition = positionComponentMapper.get(entityId);
        entityRenderable = renderableComponentMapper.get(entityId);
        batch.setColor(entityRenderable.r, entityRenderable.g, entityRenderable.b, entityRenderable.a);
        batch.draw(
                entityRenderable.texture,
                entityPosition.x,
                entityPosition.y,
                entityPosition.getOriginX(),
                entityPosition.getOriginY(),
                entityPosition.width,
                entityPosition.height,
                entityRenderable.scaleX,
                entityRenderable.scaleY,
                entityPosition.rotation,
                0, 0,
                entityRenderable.texture.getWidth(),
                entityRenderable.texture.getHeight(),
                false, false);
    }

    @Override
    protected void end() {
        batch.end();
    }
}
