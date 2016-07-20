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
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.utils.SortedEntityComponentArray;

import java.util.Comparator;

//Extend this system to draw entities to a specific camera.
//Uses a SortedEntityComponentArray to draw entities based on their Layer component.
public abstract class RenderSystem extends BaseEntitySystem {
    private ComponentMapper<Spatial> spatialComponentMapper;
    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private Spatial entitySpatial;
    private Rotatable entityRotatable;
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
            drawCurrentEntity = true;
            entitySpatial = spatialComponentMapper.get(entityId);
            drawEntityText(entityId);

            if (renderableComponentMapper.has(entityId)) {
                entityRenderable = renderableComponentMapper.get(entityId);
                setEntityGraphics();
                setSpatialData();
                setRotationData(entityId);
                if (drawCurrentEntity) {
                    batch.draw(
                            entityRenderable.texture,
                            drawX,
                            drawY,
                            drawOriginX,
                            drawOriginY,
                            drawWidth,
                            drawHeight,
                            entityRenderable.scaleX,
                            entityRenderable.scaleY,
                            drawRotation,
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

    private void setRotationData(int entityId) {
        if (rotatableComponentMapper.has(entityId)) {
            drawRotation = rotatableComponentMapper.get(entityId).getRotationInDegrees();
        } else {
            drawRotation = 0;
        }
    }

    @Override
    protected void end() {
        batch.end();
    }
}
