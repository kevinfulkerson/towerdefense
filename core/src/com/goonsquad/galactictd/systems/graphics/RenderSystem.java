package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.systems.utils.SortedEntityComponentArray;
import com.goonsquad.galactictd.components.positional.Position;

import java.util.Comparator;

//Extend this system to draw entities to a specific camera.
//Uses a SortedEntityComponentArray to draw entities based on their Layer component.
public abstract class RenderSystem extends BaseEntitySystem {
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;

    private Position entityPosition;
    private Renderable entityRenderable;

    private SortedEntityComponentArray<Layer> sortedEs;
    private Comparator<Layer> layerComparator;

    protected SpriteBatch batch;
    private Camera camera;

    public RenderSystem(Camera camera, Aspect.Builder aspect) {
        super(aspect.all(Renderable.class, Layer.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        layerComparator = new Comparator<Layer>() {
            @Override
            public int compare(Layer addedLayer, Layer currentLayer) {
                return addedLayer.layerLevel.compareTo(currentLayer.layerLevel);
            }
        };
        sortedEs = new SortedEntityComponentArray<Layer>(layerComparator, layerComponentMapper);
        batch = new SpriteBatch();
    }

    @Override
    protected void inserted(int entityId) {
        sortedEs.insert(entityId);
    }

    @Override
    protected void removed(int entityId) {
        sortedEs.remove(entityId);
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void processSystem() {
        for (int entityId : sortedEs.getSortedEntityIds()) {
            entityPosition = positionComponentMapper.get(entityId);
            entityRenderable = renderableComponentMapper.get(entityId);
            batch.setColor(entityRenderable.r, entityRenderable.g, entityRenderable.b, entityRenderable.a);
            batch.draw(
                    entityRenderable.texture,
                    entityPosition.x,
                    entityPosition.y,
                    entityPosition.width / 2f,
                    entityPosition.height / 2f,
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
    }

    @Override
    protected void end() {
        batch.end();
    }
}
