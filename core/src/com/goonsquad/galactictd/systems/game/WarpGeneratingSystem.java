package com.goonsquad.galactictd.systems.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.goonsquad.galactictd.screens.PlayScreen;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;

public class WarpGeneratingSystem extends com.artemis.BaseSystem {
    private PlayScreenArchetypeBuilder archetypeBuilder;
    private float warpCoolDownTime = 5f;
    private float currentCoolDown = warpCoolDownTime;
    private final float warpWidth = 100f;
    private final float warpHeight = 100f;
    private Vector2 warpLocation;

    public WarpGeneratingSystem() {
        warpLocation = new Vector2();
    }

    @Override
    protected void processSystem() {
        currentCoolDown -= world.getDelta();
        if (currentCoolDown <= 0) {
            currentCoolDown = warpCoolDownTime;
            generateWarpLocation();
            archetypeBuilder.createWarp(warpLocation.x, warpLocation.y, warpWidth, warpHeight);
        }
    }

    private void generateWarpLocation() {
        warpLocation.x = MathUtils.random(warpWidth, PlayScreen.GAME_WIDTH - warpWidth);
        warpLocation.y = MathUtils.random(
                warpHeight,
                PlayScreen.GAME_HEIGHT - PlayScreen.HUD_HEIGHT - warpHeight);
    }
}
