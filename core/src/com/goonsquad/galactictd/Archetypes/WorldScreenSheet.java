package com.goonsquad.galactictd.Archetypes;

import com.artemis.ComponentMapper;
import com.artemis.World;

import com.badlogic.gdx.graphics.Texture;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.positional.SetDestinationOnTouch;
import com.goonsquad.galactictd.components.positional.Position;
import com.goonsquad.galactictd.components.graphics.Renderable;

public class WorldScreenSheet extends ArchetypeSheet {

    public WorldScreenSheet(World world) {
        super(world);
    }

    @Override
    protected void createDefaultArchetypes() {
        super.createDefaultArchetypes();
        addArchetype("position", Position.class);
        addArchetype("ship", "sprite", DrawInGame.class, SetDestinationOnTouch.class, DrawBoxAround.class);
    }

    public int createShip(Texture texture) {
        ComponentMapper<Renderable> rm = world.getMapper(Renderable.class);
        ComponentMapper<Position> bm = world.getMapper(Position.class);
        int ship = this.createEntity("ship");
        bm.get(ship).height = 50;
        bm.get(ship).width = 50;
        rm.get(ship).texture = texture;
        return ship;
    }
}
