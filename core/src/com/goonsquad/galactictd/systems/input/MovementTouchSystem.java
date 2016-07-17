package com.goonsquad.galactictd.systems.input;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.goonsquad.galactictd.GalacticTDGame;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.MovementContext;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.layers.LayerLevel;
import com.goonsquad.galactictd.components.positional.Spatial;
import com.goonsquad.galactictd.systems.archetypes.PlayScreenArchetypeBuilder;

public class MovementTouchSystem extends TouchConsumerSystem {

    private PlayScreenArchetypeBuilder archetypeBuilder;

    private boolean isSetup;
    private boolean consumeInput;
    private int insertedId;

    public MovementTouchSystem(Viewport viewport) {
        super(viewport, Aspect.all(MovementContext.class));
        isSetup = false;
        consumeInput = false;
        insertedId = -1;
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.addInitialEntities();
        isSetup = true;
    }

    protected void addInitialEntities() {
        // --- Context Touch Field ---
        int contextFieldId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.CONTEXT_TOUCH_FIELD);
        Spatial contextFieldSpatial = super.spatialComponentMapper.get(contextFieldId);
        contextFieldSpatial.setBounds(0, 0, GalacticTDGame.UI_WIDTH, GalacticTDGame.UI_HEIGHT);

        // Place this entity on the third-highest level
        Layer contextFieldLayer = super.layerComponentMapper.get(contextFieldId);
        contextFieldLayer.layerLevel = LayerLevel.UI;

        // --- Context Error Field Menu Item ---
        int contextErrorFieldId = archetypeBuilder.buildArchetype(PlayScreenArchetypeBuilder.CONTEXT_MENU_ITEM);
        Spatial contextErrorFieldSpatial = super.spatialComponentMapper.get(contextFieldId);

    }

    @Override
    public void inserted(int id) {
        if(isSetup) {
            // assert flag stating that the next input on the touch field is
            // intended as the location to move to.
            insertedId = id;
            consumeInput = true;
        }
    }

    @Override
    protected void removed(int id) {
        if(isSetup) {
            // remove input handling flag
            insertedId = -1;
            consumeInput = false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // ...
        // ... do processing
        // Remove the added entity after processing
        return consumeInput;
    }
}
