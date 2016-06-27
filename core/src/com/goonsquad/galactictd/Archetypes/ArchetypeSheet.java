package com.goonsquad.galactictd.Archetypes;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.World;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.graphics.Renderable;

import java.util.HashMap;
import java.util.Map;

public abstract class ArchetypeSheet {
    protected World world;
    private Map<String, Archetype> createdArchetypes;

    public ArchetypeSheet(World world) {
        this.world = world;
        createdArchetypes = new HashMap<String, Archetype>();
        createdArchetypes.put(null, null);
        createDefaultArchetypes();
    }

    protected void createDefaultArchetypes() {
        addArchetype("sprite", Position.class, Renderable.class);
    }

    public int createEntity(String archeTypeName) {
        if (createdArchetypes.containsKey(archeTypeName)) {
            return world.create(createdArchetypes.get(archeTypeName));
        } else {
            throw new RuntimeException("Key" + archeTypeName + " not found");
        }
    }

    public void addArchetype(String archeTypeName, Class<? extends Component>... components) {
        addArchetype(archeTypeName, null, components);
    }

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
