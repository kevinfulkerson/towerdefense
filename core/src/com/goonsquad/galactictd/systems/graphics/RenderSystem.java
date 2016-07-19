package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

    private Spatial entitySpatial;
    private Renderable entityRenderable;
    private Text entityText;

    private SortedEntityComponentArray<Layer> sortedEs;
    private Comparator<Layer> layerComparator;

    protected SpriteBatch batch;
    private Camera camera;
    private Texture missingTextureImage;

    private float drawX;
    private float drawY;
    private float drawOriginX;
    private float drawOriginY;
    private float drawWidth;
    private float drawHeight;
    private float drawRotation;
    private boolean drawCurrentEntity;

    public RenderSystem(Camera camera, Aspect.Builder aspect, Texture missingTextureImage) {
        super(aspect.all(Layer.class).one(Text.class, Renderable.class));
        this.camera = camera;
        this.missingTextureImage = missingTextureImage;
        this.batch = new SpriteBatch();
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
                Text text = textComponentMapper.get(entityId);
                text.font.draw(batch, text.text, entitySpatial.x, entitySpatial.y);
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

    private void drawEntityText(int entityId) {
        if (textComponentMapper.has(entityId)) {
            entityText = textComponentMapper.get(entityId);
            entityText.font.draw(batch, entityText.text, entitySpatial.x, entitySpatial.y);
        }
    }

    private void setEntityGraphics() {
        batch.setColor(entityRenderable.r, entityRenderable.g, entityRenderable.b, entityRenderable.a);
        if (entityRenderable.texture == null)
            entityRenderable.texture = missingTextureImage;
    }

    private void setSpatialData() {
        if (entitySpatial.spatialType == BoundsType.Rectangle) {
            drawX = entitySpatial.x;
            drawY = entitySpatial.y;
            drawOriginX = entitySpatial.width / 2f;
            drawOriginY = entitySpatial.height / 2f;
            drawWidth = entitySpatial.width;
            drawHeight = entitySpatial.height;
        } else if (entitySpatial.spatialType == BoundsType.Circle) {
            drawX = entitySpatial.centerX - entitySpatial.radius;
            drawY = entitySpatial.centerY - entitySpatial.radius;
            drawOriginX = entitySpatial.radius;
            drawOriginY = entitySpatial.radius;
            drawWidth = entitySpatial.radius * 2;
            drawHeight = entitySpatial.radius * 2;
        } else {
            drawCurrentEntity = false;
        }
    }

    private void setRotationData() {
        drawRotation = entitySpatial.rotation;
    }

    @Override
    protected void end() {
        batch.end();
    }
}
