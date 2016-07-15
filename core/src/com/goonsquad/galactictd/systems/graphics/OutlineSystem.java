package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.positional.BoundsType;
import com.goonsquad.galactictd.components.positional.Spacial;

//This system exists only for debugging purposes.
public class OutlineSystem extends IteratingSystem {
    ComponentMapper<Spacial> positionComponentMapper;

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    public OutlineSystem(Camera camera) {
        super(Aspect.all(Spacial.class, DrawBoxAround.class));
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
        Spacial entitySpacial = positionComponentMapper.get(entityId);
        if (entitySpacial.spacialType == BoundsType.Rectangle) {
            shapeRenderer.rect(entitySpacial.x, entitySpacial.y, entitySpacial.width, entitySpacial.height);
        } else if (entitySpacial.spacialType == BoundsType.Circle) {
            shapeRenderer.circle(entitySpacial.centerX, entitySpacial.centerY, entitySpacial.radius);
        }
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }
}
