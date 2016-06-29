package com.goonsquad.galactictd.systems.graphics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.positional.Position;


public class BoxRenderSystem extends IteratingSystem {
    ComponentMapper<Position> positionComponentMapper;

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    public BoxRenderSystem(Camera camera) {
        super(Aspect.all(Position.class, DrawBoxAround.class));
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
        Position p = positionComponentMapper.get(entityId);
        shapeRenderer.rect(p.x, p.y, p.width, p.height);
    }

    @Override
    protected void end() {
        shapeRenderer.end();
    }
}
