package com.goonsquad.galactictd.systems.initialization;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.graphics.Text;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.positional.MoveToPoint;
import com.goonsquad.galactictd.components.positional.MovementDestination;
import com.goonsquad.galactictd.components.positional.MovementSpeed;
import com.goonsquad.galactictd.components.positional.Rotatable;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.components.positional.ResetPosition;
import com.goonsquad.galactictd.screens.HomeScreen;
import com.goonsquad.galactictd.systems.archetypes.ScoreScreenArchetypeBuilder;

public class ScoreScreenInitSystem extends InitializationSystem {
    private ScoreScreenArchetypeBuilder archetypeBuilder;
    private ComponentMapper<Spatial> spatialComponentMapper;
    private ComponentMapper<Rotatable> rotatableComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<MoveToPoint> moveToPointComponentMapper;
    private ComponentMapper<MovementDestination> movementDestinationComponentMapper;
    private ComponentMapper<MovementSpeed> movementSpeedComponentMapper;
    private ComponentMapper<ResetPosition> resetPositionComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private GalacticTDGame gameInstance;
    private Vector2 shipSize;
    private float shipSpeedPerSecond;

    public ScoreScreenInitSystem(GalacticTDGame game) {
        this.gameInstance = game;
        shipSize = new Vector2(150f, 150f);
        shipSpeedPerSecond = 750f;
    }

    @Override
    protected void populateWorld() {
        createChangeScreenButton();
        createRedShip();
        createGreenShip();
        createHighScoreLabels();
    }

    private int createChangeScreenButton() {
        int changeScreenButton = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.invisibleButton);

        Spatial buttonSpatial = spatialComponentMapper.get(changeScreenButton);
        buttonSpatial.setBounds(0, 0, GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

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
        redRenderable.texture = gameInstance.assets.manager.get("tower-red.png", Texture.class);

        ResetPosition redStartingCords = resetPositionComponentMapper.get(redShip);
        redStartingCords.resetPositionX = 0 - shipSize.x;
        redStartingCords.resetPositionY = 0;

        Spatial redSpatial = spatialComponentMapper.get(redShip);
        redSpatial.setBounds(
                redStartingCords.resetPositionX, redStartingCords.resetPositionY,
                shipSize.x, shipSize.y);

        Rotatable redRotatable = rotatableComponentMapper.get(redShip);
        redRotatable.rotationInRadians = 1.5f * MathUtils.PI;

        MoveToPoint moveToPoint = moveToPointComponentMapper.get(redShip);
        moveToPoint.resetPositionOnArrival = true;
        moveToPoint.moving = true;

        MovementDestination movementDestination = movementDestinationComponentMapper.create(redShip);
        movementDestination.destinationX = GalacticTDGame.UI_WIDTH + redSpatial.width;
        movementDestination.destinationY = redSpatial.y + redSpatial.height / 2f;

        MovementSpeed speed = movementSpeedComponentMapper.create(redShip);
        speed.unitsPerSecond = shipSpeedPerSecond;
        return redShip;
    }

    private int createGreenShip() {
        int greenShip = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.ship);

        Renderable greenRenderable = renderableComponentMapper.get(greenShip);
        greenRenderable.texture = gameInstance.assets.manager.get("tower-green.png", Texture.class);

        ResetPosition greenStartingCords = resetPositionComponentMapper.get(greenShip);
        greenStartingCords.resetPositionX = GalacticTDGame.UI_WIDTH;
        greenStartingCords.resetPositionY = GalacticTDGame.UI_HEIGHT - shipSize.y;

        Spatial greenSpatial = spatialComponentMapper.get(greenShip);
        greenSpatial.setBounds(
                greenStartingCords.resetPositionX, greenStartingCords.resetPositionY,
                shipSize.x, shipSize.y);

        Rotatable greenRotatable = rotatableComponentMapper.get(greenShip);
        greenRotatable.rotationInRadians = 0.5f * MathUtils.PI;

        MoveToPoint moveToPoint = moveToPointComponentMapper.get(greenShip);
        moveToPoint.resetPositionOnArrival = true;
        moveToPoint.moving = true;

        MovementDestination movementDestination = movementDestinationComponentMapper.create(greenShip);
        movementDestination.destinationX = 0 - shipSize.x * 2;
        movementDestination.destinationY = greenSpatial.y + greenSpatial.height / 2f;

        MovementSpeed speed = movementSpeedComponentMapper.create(greenShip);
        speed.unitsPerSecond = shipSpeedPerSecond;
        return greenShip;
    }

    /**
     * Creates the high score labels.
     */
    //TODO Link score text to scores in ScoreManager.
    private void createHighScoreLabels() {
        BitmapFont scoresFont = gameInstance.assets.manager.get("nixie48.ttf", BitmapFont.class);

        Vector2 highScoreLabelPos = new Vector2(GalacticTDGame.UI_WIDTH * 0.20f, GalacticTDGame.UI_HEIGHT * 0.75f);
        createLabel("High Scores:", scoresFont, highScoreLabelPos.x, highScoreLabelPos.y);

        Vector2 highScoreListStartPos = new Vector2(GalacticTDGame.UI_WIDTH * 0.60f, GalacticTDGame.UI_HEIGHT * 0.75f);
        final float scoreLabelHeightDistance = 100f;
        createLabel("0", scoresFont, highScoreListStartPos.x, highScoreListStartPos.y);
        createLabel("0", scoresFont, highScoreListStartPos.x, highScoreListStartPos.y - (scoreLabelHeightDistance));
        createLabel("0", scoresFont, highScoreListStartPos.x, highScoreListStartPos.y - (scoreLabelHeightDistance * 2));
        createLabel("0", scoresFont, highScoreListStartPos.x, highScoreListStartPos.y - (scoreLabelHeightDistance * 3));
        createLabel("0", scoresFont, highScoreListStartPos.x, highScoreListStartPos.y - (scoreLabelHeightDistance * 4));
    }

    /**
     * Creates a label with the given params.
     *
     * @param labelText The text that the label will render.
     * @param labelFont The font used by the label.
     * @param xPos      The position of the left side of the label.
     * @param yPos      The position of the top side of the label.
     */
    private void createLabel(String labelText, BitmapFont labelFont, float xPos, float yPos) {
        int label = archetypeBuilder.buildArchetype(ScoreScreenArchetypeBuilder.textLabel);

        Text labelTextComp = textComponentMapper.get(label);
        labelTextComp.text = labelText;
        labelTextComp.font = labelFont;

        Spatial labelSpatial = spatialComponentMapper.get(label);
        labelSpatial.x = xPos;
        labelSpatial.y = yPos;
    }
}
