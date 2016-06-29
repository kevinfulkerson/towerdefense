package com.goonsquad.galactictd.systems.archtypes;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.positional.Position;

import java.util.HashMap;
import java.util.Map;

public abstract class ArchetypeBuilderSystem extends BaseSystem {
    private Map<String, Archetype> createdArchetypes;

    public ArchetypeBuilderSystem() {
        createdArchetypes = new HashMap<String, Archetype>();
        createdArchetypes.put(null, null);
        this.setEnabled(false);
    }

    @Override
    protected void initialize() {
        createDefaultArchetypes();
        createCustomArchetypes();
    }

    private void createDefaultArchetypes() {
        this.addArchetypeToSystem("sprite", Position.class, Renderable.class, DrawBoxAround.class);
        this.addArchetypeToSystem("ui_button", "sprite", DrawInUi.class);
        this.addArchetypeToSystem("ui_label", "sprite", DrawInUi.class);
    }

    protected abstract void createCustomArchetypes();

    @Override
    protected void processSystem() {
    }

    public int buildArchetype(String archetypeKey) {
        if (createdArchetypes.containsKey(archetypeKey)) {
            return world.create(createdArchetypes.get(archetypeKey));
        } else {
            throw new RuntimeException("Key" + archetypeKey + " not found");
        }
    }

    //Add an archetype to the pool of archetypes that doesn't inherit any components from a parent archetype.
    public void addArchetypeToSystem(String archetypeKey, Class<? extends Component>... components) {
        addArchetypeToSystem(archetypeKey, null, components);
    }

    //Creates an archetype that can be referenced by ar
    public void addArchetypeToSystem(String newArcheTypeName, String parentArchetypeName, Class<? extends Component>... components) {
        Archetype archeType = createdArchetypes.get(newArcheTypeName);
        if (archeType == null) {
            Archetype parent = createdArchetypes.get(parentArchetypeName);
            archeType = new ArchetypeBuilder(parent).add(components).build(world);
            createdArchetypes.put(newArcheTypeName, archeType);
        } else {
            throw new RuntimeException("Archetype with name " + newArcheTypeName + "already created.");
        }
    }
}
