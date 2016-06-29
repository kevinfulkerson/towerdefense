package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.StartingCords;
import com.goonsquad.galactictd.systems.archtypes.ScoreScreenArchetypeBuilder;

public class ScoreScreenInitSystem extends InitializationSystem {
    private ScoreScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<StartingCords> startingCordsComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;

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

        StartingCords redStartingCords = startingCordsComponentMapper.get(redShip);
        redStartingCords.x = 0 - shipSize.x;
        redStartingCords.y = 0;

        Position redPosition = positionComponentMapper.get(redShip);
        redPosition.rotation = 90;
        redPosition.setBounds(
                redStartingCords.x, redStartingCords.y,
                shipSize.x, shipSize.y);


        MoveToPoint moveToPoint = moveToPointComponentMapper.get(redShip);
        moveToPoint.destinationX = GalacticTDGame.UI_WIDTH;
        moveToPoint.destinationY = redPosition.y;
        moveToPoint.speedPerSecond = 500f;
        moveToPoint.returnToStartingCords = true;
        return redShip;
    }

    private int createGreenShip() {
        int greenShip = archetypeBuilder.buildArchetype("ship");

        Renderable greenRenderable = renderableComponentMapper.get(greenShip);
        greenRenderable.texture = gameInstance.getAssetManager().get("tower-green.png", Texture.class);

        StartingCords greenStartingCords = startingCordsComponentMapper.get(greenShip);
        greenStartingCords.x = GalacticTDGame.UI_WIDTH;
        greenStartingCords.y = GalacticTDGame.UI_HEIGHT - shipSize.y;

        Position greenPosition = positionComponentMapper.get(greenShip);
        greenPosition.rotation = 270;
        greenPosition.setBounds(
                greenStartingCords.x, greenStartingCords.y,
                shipSize.x, shipSize.y);

        MoveToPoint moveToPoint = moveToPointComponentMapper.get(greenShip);
        moveToPoint.destinationX = 0 - shipSize.x;
        moveToPoint.destinationY = greenPosition.y;
        moveToPoint.speedPerSecond = 500f;
        moveToPoint.returnToStartingCords = true;
        return greenShip;
    }
}
