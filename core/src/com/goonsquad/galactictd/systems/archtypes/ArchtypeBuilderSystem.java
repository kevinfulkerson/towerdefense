package com.goonsquad.galactictd.systems.archtypes;

import com.artemis.Archetype;
import com.artemis.BaseSystem;

import java.util.HashMap;
import java.util.Map;

public class ArchtypeBuilderSystem extends BaseSystem {
    private Map<String, Archetype> createdArchetypes;

    public ArchtypeBuilderSystem() {
        createdArchetypes = new HashMap<String, Archetype>();
        createdArchetypes.put(null, null);
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
}
