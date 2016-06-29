package com.goonsquad.galactictd.systems.archtypes;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.BaseSystem;
import com.artemis.Component;

import java.util.HashMap;
import java.util.Map;

public class ArchetypeBuilderSystem extends BaseSystem {
    private Map<String, Archetype> createdArchetypes;

    public ArchetypeBuilderSystem() {
        createdArchetypes = new HashMap<String, Archetype>();
        createdArchetypes.put(null, null);
        this.setEnabled(false);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void processSystem() {

    }

    public int build(String archtypeKey) {
        if (createdArchetypes.containsKey(archtypeKey)) {
            return world.create(createdArchetypes.get(archtypeKey));
        } else {
            throw new RuntimeException("Key" + archtypeKey + " not found");
        }
    }

    //Creates an archtype that doesn't inherit any components from a parent archtype.
    public void addArchetype(String archetypeKey, Class<? extends Component>... components) {
        addArchetype(archetypeKey, null, components);
    }

    //Creates an archtype that can be referenced by ar
    public void addArchetype(String archeTypeName, String parentName, Class<? extends Component>... components) {
        Archetype archeType = createdArchetypes.get(archeTypeName);
        if (archeType == null) {
            Archetype parent = createdArchetypes.get(parentName);
            archeType = new ArchetypeBuilder(parent).add(components).build(world);
            createdArchetypes.put(archeTypeName, archeType);
        } else {
            throw new RuntimeException("Archetype already created.");
        }
    }
}
