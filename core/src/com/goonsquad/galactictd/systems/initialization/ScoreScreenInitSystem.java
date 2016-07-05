package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.graphics.Text;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.screens.HomeScreen;
import com.goonsquad.galactictd.systems.archetypes.ScoreScreenArchetypeBuilder;

public class ScoreScreenInitSystem extends InitializationSystem {
    private ScoreScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<MovementDestination> movementDestinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private GalacticTDGame gameInstance;
    private Vector2 shipSize;
    private BitmapFont font;
    private float shipSpeedPerSecond;

    public ScoreScreenInitSystem(GalacticTDGame game, BitmapFont font) {
        this.gameInstance = game;
        shipSize = new Vector2(150f, 150f);
        shipSpeedPerSecond = 750f;
        this.font = font;
    }

    @Override
    protected void populateWorld() {
        createChangeScreenButton();
        createRedShip();
        createGreenShip();
        createText();
    }

    private int createChangeScreenButton() {
        int changeScreenButton = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.invisibleButton);

        Position buttonPosition = positionComponentMapper.get(changeScreenButton);
        buttonPosition.setBounds(0, 0, GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

        Touchable buttonTouchable = touchableComponentMapper.get(changeScreenButton);
        buttonTouchable.event = new Event() {
            @Override
            public void fireEvent() {
                gameInstance.getScreenManager().setScreen(HomeScreen.class);
            }
        };

        return changeScreenButton;
    }

    private int createRedShip() {
        int redShip = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.ship);

        Renderable redRenderable = renderableComponentMapper.get(redShip);
        redRenderable.texture = gameInstance.getAssetManager().get("tower-red.png", Texture.class);

        ResetPosition redStartingCords = resetPositionComponentMapper.get(redShip);
        redStartingCords.resetPositionX = 0 - shipSize.x;
        redStartingCords.resetPositionY = 0;

        Position redPosition = positionComponentMapper.get(redShip);
        redPosition.rotation = 270;
        redPosition.setBounds(
                redStartingCords.resetPositionX, redStartingCords.resetPositionY,
                shipSize.x, shipSize.y);


        MoveToPoint moveToPoint = moveToPointComponentMapper.get(redShip);
        moveToPoint.resetPositionOnArrival = true;
        moveToPoint.moving = true;

        MovementDestination movementDestination = movementDestinationComponentMapper.create(redShip);
        movementDestination.destinationX = GalacticTDGame.UI_WIDTH + redPosition.width;
        movementDestination.destinationY = redPosition.y;

        MovementSpeed speed = movementSpeedComponentMapper.create(redShip);
        speed.unitsPerSecond = shipSpeedPerSecond;
        return redShip;
    }

    private int createGreenShip() {
        int greenShip = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.ship);

        Renderable greenRenderable = renderableComponentMapper.get(greenShip);
        greenRenderable.texture = gameInstance.getAssetManager().get("tower-green.png", Texture.class);

        ResetPosition greenStartingCords = resetPositionComponentMapper.get(greenShip);
        greenStartingCords.resetPositionX = GalacticTDGame.UI_WIDTH;
        greenStartingCords.resetPositionY = GalacticTDGame.UI_HEIGHT - shipSize.y;

        Position greenPosition = positionComponentMapper.get(greenShip);
        greenPosition.rotation = 90;
        greenPosition.setBounds(
                greenStartingCords.resetPositionX, greenStartingCords.resetPositionY,
                shipSize.x, shipSize.y);

        MoveToPoint moveToPoint = moveToPointComponentMapper.get(greenShip);
        moveToPoint.resetPositionOnArrival = true;
        moveToPoint.moving = true;

        MovementDestination movementDestination = movementDestinationComponentMapper.create(greenShip);
        movementDestination.destinationX = 0 - shipSize.x * 2;
        movementDestination.destinationY = greenPosition.y;

        MovementSpeed speed = movementSpeedComponentMapper.create(greenShip);
        speed.unitsPerSecond = shipSpeedPerSecond;
        return greenShip;
    }

    private void createText() {
        int label = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.textLabel);

        Text labelText = textComponentMapper.get(label);
        labelText.text = "Hello, World";
        labelText.font = font;

        Position labelPosition = positionComponentMapper.get(label);
        labelPosition.setBounds(50, 50, 50, 50);
    }
}
