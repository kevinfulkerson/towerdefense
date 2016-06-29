package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.archetypesdeprecated.ArchetypeSheet;
import com.goonsquad.galactictd.Archetypes.WorldScreenSheet;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.Position;

public class WorldScreenInitSystemOld extends InitializationSystemOld {
    ComponentMapper<Renderable> renderableComponentMapper;
    ComponentMapper<MoveToPoint> moveMapper;
    ComponentMapper<Position> positionComponentMapper;
    GalacticTDGame gameInstance;

    public WorldScreenInitSystemOld(ArchetypeSheet archeSheet, GalacticTDGame game) {
        super(archeSheet);
        this.gameInstance = game;
    }

    @Override
    protected void initializeSheet() {
        this.sheet = new WorldScreenSheet(this.world);
    }

    @Override
    protected void populateWorld() {
        WorldScreenSheet wss = (WorldScreenSheet) this.sheet;
        createShip(wss);
    }

    private void createShip(WorldScreenSheet wss) {
        int ship = wss.createShip(gameInstance.getAssetManager().get("fireBall.png", Texture.class));
        Renderable renderable = renderableComponentMapper.get(ship);
        renderable.g = .5f;
        renderable.rotation = 90f;

        MoveToPoint location = moveMapper.create(ship);
        location.speedPerSecond = 1000f;
        location.destinationX = 100f;
        location.destinationY = 0f;
        location.rotateTowardsPoint = true;

        Position position = positionComponentMapper.get(ship);
        position.x = 500;
        position.y = 500;
    }
}
