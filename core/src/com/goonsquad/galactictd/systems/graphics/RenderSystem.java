package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.graphics.Text;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.positional.BoundsType;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.utils.SortedEntityComponentArray;

import java.util.Comparator;

//Extend this system to draw entities to a specific camera.
//Uses a SortedEntityComponentArray to draw entities based on their Layer component.
public abstract class RenderSystem extends BaseEntitySystem {
    private ComponentMapper<Spatial> spatialComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private Text entityText;
    private Spatial entitySpatial;
    private Renderable entityRenderable;

    private SortedEntityComponentArray<Layer> sortedEs;
    private Comparator<Layer> layerComparator;

    protected SpriteBatch batch;
    private Camera camera;
    private Texture missingTextureImage;

    public RenderSystem(Camera camera, Aspect.Builder aspect, Texture missingTextureImage) {
        super(aspect.all(Layer.class).one(Text.class, Renderable.class));
        this.camera = camera;
        this.missingTextureImage = missingTextureImage;
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
        sortedEs.insertValue(entityId);
    }

    @Override
    protected void removed(int entityId) {
        sortedEs.removeValue(entityId);
    }

    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void processSystem() {
        for (int entityId : sortedEs) {
            entitySpatial = spatialComponentMapper.get(entityId);
            if (textComponentMapper.has(entityId)) {
                entityText = textComponentMapper.get(entityId);
                entityText.font.draw(batch, entityText.text, entitySpatial.x, entitySpatial.y);
            }
            if (renderableComponentMapper.has(entityId)) {
                entityRenderable = renderableComponentMapper.get(entityId);
                if (entityRenderable.texture == null)
                    entityRenderable.texture = missingTextureImage;
                batch.setColor(entityRenderable.r, entityRenderable.g, entityRenderable.b, entityRenderable.a);
                if (entitySpatial.spatialType == BoundsType.Rectangle) {
                    batch.draw(
                            entityRenderable.texture,
                            entitySpatial.x,
                            entitySpatial.y,
                            entitySpatial.width / 2f,
                            entitySpatial.height / 2f,
                            entitySpatial.width,
                            entitySpatial.height,
                            entityRenderable.scaleX,
                            entityRenderable.scaleY,
                            entitySpatial.rotation,
                            0, 0,
                            entityRenderable.texture.getWidth(),
                            entityRenderable.texture.getHeight(),
                            false, false);
                } else if (entitySpatial.spatialType == BoundsType.Circle) {
                    batch.draw(
                            entityRenderable.texture,
                            entitySpatial.centerX - entitySpatial.radius,
                            entitySpatial.centerY - entitySpatial.radius,
                            entitySpatial.radius,
                            entitySpatial.radius,
                            entitySpatial.radius * 2,
                            entitySpatial.radius * 2,
                            entityRenderable.scaleX,
                            entityRenderable.scaleY,
                            entitySpatial.rotation,
                            0, 0,
                            entityRenderable.texture.getWidth(),
                            entityRenderable.texture.getHeight(),
                            false, false);
                }
            }
        }
    }

    @Override
    protected void end() {
        batch.end();
    }
}
