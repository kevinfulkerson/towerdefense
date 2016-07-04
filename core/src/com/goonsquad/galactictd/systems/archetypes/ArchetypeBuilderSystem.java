package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.positional.Position;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;

public abstract class ArchetypeBuilderSystem extends BaseSystem {
    private Map<String, Archetype> createdArchetypes;

    public ArchetypeBuilderSystem() {
        createdArchetypes = new HashMap<String, Archetype>();
        createdArchetypes.put(null, null);
        this.setEnabled(false);
    }

    @Override
    protected final void initialize() {
        createDefaultArchetypes();
        createCustomArchetypes();
    }

    //Archetypes that are used in all worlds.
    private void createDefaultArchetypes() {
        this.addArchetypeToSystem("invisible_button", Position.class, Layer.class, Touchable.class, DrawInUi.class);

        this.addArchetypeToSystem("sprite", Position.class, Renderable.class, DrawBoxAround.class, Layer.class);

        this.addArchetypeToSystem("ui_label", "sprite", DrawInUi.class);
        this.addArchetypeToSystem("ui_button", "ui_label", Touchable.class);
    }

    //Overwrite to create archetypes specific to each world.
    protected abstract void createCustomArchetypes();


    //Creates an entity of the given archetype.
    //New entity will have all the components of the archetype.
    //Exception is thrown if the passed in archetype is not found.
    //
    public int buildArchetype(String archetypeKey) {
        if (createdArchetypes.containsKey(archetypeKey)) {
            return world.create(createdArchetypes.get(archetypeKey));
        } else {
            throw new RuntimeException("Key" + archetypeKey + " not found");
        }
    }

    //  ** Passed in components must have an empty constructor **
    //Adds an archetype to the pool of previously made archetypes,
    //archetype can then be accessed by the key name passed in,
    public void addArchetypeToSystem(String archetypeKey, Class<? extends Component>... components) {
        addArchetypeToSystem(archetypeKey, null, components);
    }

    //  ** Passed in components must have an empty constructor **
    //If parent archetype is found, the new archetype will inherit all the components of the parent.
    //Attempting to create a new archetype that is already set will result in an exception.
    public void addArchetypeToSystem(String newArcheTypeName, String parentArchetypeName, Class<? extends Component>... components) {
        if (!createdArchetypes.containsKey(newArcheTypeName)) {
            Archetype parent = createdArchetypes.get(parentArchetypeName);
            Archetype newArcheType = new ArchetypeBuilder(parent).add(components).build(world);
            createdArchetypes.put(newArcheTypeName, newArcheType);
        } else {
            throw new RuntimeException("Archetype with name " + newArcheTypeName + "already created.");
        }
    }

    @Override
    protected void processSystem() {
    }
}
