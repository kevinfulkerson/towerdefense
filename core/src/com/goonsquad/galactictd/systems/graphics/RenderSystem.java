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
import com.goonsquad.galactictd.components.positional.Spacial;
import com.goonsquad.galactictd.systems.utils.SortedEntityComponentArray;

import java.util.Comparator;

//Extend this system to draw entities to a specific camera.
//Uses a SortedEntityComponentArray to draw entities based on their Layer component.
public abstract class RenderSystem extends BaseEntitySystem {
    private ComponentMapper<Spacial> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Layer> layerComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private Spacial entitySpacial;
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
        for (int entityId : sortedEs) {
            entitySpacial = positionComponentMapper.get(entityId);
            if (textComponentMapper.has(entityId)) {
                Text text = textComponentMapper.get(entityId);
                text.font.draw(batch, text.text, entitySpacial.x, entitySpacial.y);
            }
            if (renderableComponentMapper.has(entityId)) {
                entityRenderable = renderableComponentMapper.get(entityId);
                if (entityRenderable.texture == null)
                    entityRenderable.texture = missingTextureImage;
                batch.setColor(entityRenderable.r, entityRenderable.g, entityRenderable.b, entityRenderable.a);
                if (entitySpacial.spacialType == BoundsType.Rectangle) {
                    batch.draw(
                            entityRenderable.texture,
                            entitySpacial.x,
                            entitySpacial.y,
                            entitySpacial.width / 2f,
                            entitySpacial.height / 2f,
                            entitySpacial.width,
                            entitySpacial.height,
                            entityRenderable.scaleX,
                            entityRenderable.scaleY,
                            entitySpacial.rotation,
                            0, 0,
                            entityRenderable.texture.getWidth(),
                            entityRenderable.texture.getHeight(),
                            false, false);
                } else if (entitySpacial.spacialType == BoundsType.Circle) {
                    batch.draw(
                            entityRenderable.texture,
                            entitySpacial.centerX - entitySpacial.radius,
                            entitySpacial.centerY - entitySpacial.radius,
                            entitySpacial.radius,
                            entitySpacial.radius,
                            entitySpacial.radius * 2,
                            entitySpacial.radius * 2,
                            entityRenderable.scaleX,
                            entityRenderable.scaleY,
                            entitySpacial.rotation,
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
