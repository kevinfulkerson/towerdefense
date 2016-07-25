package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.positional.BoundsType;
import com.goonsquad.galactictd.components.positional.Spatial;

//This system exists only for debugging purposes.
public class OutlineSystem extends IteratingSystem {
    ComponentMapper<Spatial> spatialComponentMapper;

    private Spatial shapeSpacial;
    private ShapeRenderer shapeRenderer;
    private Camera camera;

    public OutlineSystem(Camera camera) {
        super(Aspect.all(Spatial.class, DrawBoxAround.class));
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        this.shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    protected void begin() {
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
    }


    @Override
    protected void process(int entityId) {
        shapeSpacial = spatialComponentMapper.get(entityId);
        if (shapeSpacial.spatialType == BoundsType.Rectangle) {
            shapeRenderer.rect(shapeSpacial.x, shapeSpacial.y, shapeSpacial.width, shapeSpacial.height);
        } else if (shapeSpacial.spatialType == BoundsType.Circle) {
            shapeRenderer.circle(shapeSpacial.centerX, shapeSpacial.centerY, shapeSpacial.radius);
        }
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }
}
