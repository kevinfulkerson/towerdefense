package com.goonsquad.galactictd.systems.initialization;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.screens.GameScreen;
import com.goonsquad.galactictd.systems.archetypes.GameScreenArchetypeBuilder;

public class GameScreenInitSystem extends InitializationSystem {
    private GameScreenArchetypeBuilder archetypeBuilder;
    private Vector2 spriteBounds;
    private GalacticTDGame gameInstance;
    private Texture frank;

    public GameScreenInitSystem(GalacticTDGame game) {
        gameInstance = game;
        spriteBounds = new Vector2(120, 120);
        frank = gameInstance.assets.manager.get("Owens_Frank.jpg", Texture.class);
    }

    @Override
    protected void populateWorld() {
        createTopLeftSprite();
        createTopRightSprite();
        createCenterSprite();
        createBottomLeftSprite();
        createBottomRightSprite();
    }

    private void createTopLeftSprite() {
        int topLeft = archetypeBuilder.createGameSprite(
                0, GameScreen.worldHeight - spriteBounds.y,
                spriteBounds.x, spriteBounds.y,
                frank);
    }


    private void createTopRightSprite() {
        int topRight = archetypeBuilder.createGameSprite(
                GameScreen.worldWidth - spriteBounds.x, GameScreen.worldHeight - spriteBounds.y,
                spriteBounds.x, spriteBounds.y,
                frank);
    }

    private void createCenterSprite() {
        int center = archetypeBuilder.createGameSprite(
                GameScreen.worldWidth / 2f - spriteBounds.x / 2f,
                GameScreen.worldHeight / 2f - spriteBounds.y / 2f,
                spriteBounds.x, spriteBounds.y,
                frank);
    }

    private void createBottomLeftSprite() {
        int bottomLeft = archetypeBuilder.createGameSprite(
                0, 0,
                spriteBounds.x, spriteBounds.y,
                frank);
    }

    private void createBottomRightSprite() {
        int bottomRight = archetypeBuilder.createGameSprite(
                GameScreen.worldWidth - spriteBounds.x, 0,
                spriteBounds.x, spriteBounds.y,
                frank);
    }
}
