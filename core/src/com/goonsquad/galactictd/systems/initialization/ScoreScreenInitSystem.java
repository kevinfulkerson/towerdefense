package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.StartingCords;
import com.goonsquad.galactictd.systems.archtypes.ScoreScreenArchetypeBuilder;

public class ScoreScreenInitSystem extends InitializationSystem {
    private ScoreScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<StartingCords> startingCordsComponentMapper;

    private GalacticTDGame gameInstance;
    private Vector2 shipSize;

    public ScoreScreenInitSystem(GalacticTDGame game) {
        this.gameInstance = game;
        shipSize = new Vector2(150f, 150f);
    }

    @Override
    protected void populateWorld() {
        createRedShip();
        createGreenShip();
    }

    private int createRedShip() {
        int redShip = archetypeBuilder.buildArchetype("ship");

        Renderable redRenderable = renderableComponentMapper.get(redShip);
        redRenderable.texture = gameInstance.getAssetManager().get("tower-red.png", Texture.class);
        redRenderable.rotation = -90;

        StartingCords redStartingCords = startingCordsComponentMapper.get(redShip);
        redStartingCords.x = 0;
        redStartingCords.y = 0;

        Position redPosition = positionComponentMapper.get(redShip);
        redPosition.setBounds(
                redStartingCords.x, redStartingCords.y,
                shipSize.x, shipSize.y);
        return redShip;
    }

    private int createGreenShip() {
        int greenShip = archetypeBuilder.buildArchetype("sprite");

        return greenShip;
    }
}
