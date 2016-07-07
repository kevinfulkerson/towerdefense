package com.goonsquad.galactictd.systems.archetypes;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.BaseSystem;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.goonsquad.galactictd.components.graphics.DrawBoxAround;
import com.goonsquad.galactictd.components.graphics.DrawInGame;
import com.goonsquad.galactictd.components.graphics.DrawInUi;
import com.goonsquad.galactictd.components.graphics.Renderable;
import com.goonsquad.galactictd.components.graphics.Text;
import com.goonsquad.galactictd.components.input.Event;
import com.goonsquad.galactictd.components.input.Touchable;
import com.goonsquad.galactictd.components.layers.Layer;
import com.goonsquad.galactictd.components.positional.Position;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Pos;

public abstract class ArchetypeBuilderSystem extends BaseSystem {
    private ComponentMapper<Position> positionComponentMapper;
    private ComponentMapper<Renderable> renderableComponentMapper;
    private ComponentMapper<Touchable> touchableComponentMapper;
    private ComponentMapper<Text> textComponentMapper;

    private Map<String, Archetype> createdArchetypes;
    public static final String boundedBox = "bounded_box";

    public static final String sprite = "sprite";
    public static final String uiSprite = "ui_sprite";
    public static final String gameSprite = "game_sprite";

    public static final String uiButton = "ui_button";
    public static final String gameButton = "game_button";
    public static final String uiText = "ui_text";

    public static final String invisibleButton = "invisible_button";

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

    /**
     * Archetypes that are used in all worlds.
     */
    private void createDefaultArchetypes() {
        this.addArchetypeToSystem(boundedBox, Position.class, Layer.class, DrawBoxAround.class);

        this.addArchetypeToSystem(sprite, boundedBox, Renderable.class);
        this.addArchetypeToSystem(uiSprite, sprite, DrawInUi.class);
        this.addArchetypeToSystem(gameSprite, sprite, DrawInGame.class);

        this.addArchetypeToSystem(uiButton, uiSprite, Touchable.class);
        this.addArchetypeToSystem(gameButton, gameSprite, Touchable.class);

        this.addArchetypeToSystem(uiText, Position.class, Text.class, Layer.class, DrawInUi.class);
        this.addArchetypeToSystem(invisibleButton, Position.class, Layer.class, Touchable.class, DrawInUi.class);
    }

    //Overwrite to create archetypes specific to each world.
    protected abstract void createCustomArchetypes();


    /**
     * Add an entity to the world with the components of the archetype.
     *
     * @param archetypeKey Name of archetype to use.
     * @return Id of newly created entity.
     */
    public int buildArchetype(String archetypeKey) {
        if (createdArchetypes.containsKey(archetypeKey)) {
            return world.create(createdArchetypes.get(archetypeKey));
        } else {
            throw new RuntimeException("Key" + archetypeKey + " not found");
        }
    }

    /**
     * Adds an archetype with the given name and components to the world.
     * Components classes must have an empty constructor.
     *
     * @param archetypeKey Name of the new archetype.
     * @param components   List of components assigned to this archetype.
     */
    public void addArchetypeToSystem(String archetypeKey, Class<? extends Component>... components) {
        addArchetypeToSystem(archetypeKey, null, components);
    }

    /**
     * Adds an archetype with the given name and components to the world.
     *
     * @param newArcheTypeName    Name of the new archetype.
     * @param parentArchetypeName Name of archetype to inherit components from. Pass in null to specify no parent.
     * @param components          List of components assigned to this archetype.
     */
    public void addArchetypeToSystem(String newArcheTypeName, String parentArchetypeName, Class<? extends Component>... components) {
        if (!createdArchetypes.containsKey(newArcheTypeName)) {
            Archetype parent = createdArchetypes.get(parentArchetypeName);
            Archetype newArcheType = new ArchetypeBuilder(parent).add(components).build(world);
            createdArchetypes.put(newArcheTypeName, newArcheType);
        } else {
            throw new RuntimeException("Archetype with name " + newArcheTypeName + "already created.");
        }
    }

    /**
     * Creates a label with the given params.
     *
     * @param labelText The text that the label will render.
     * @param labelFont The font used by the label.
     * @param xPos      The position of the left side of the label.
     * @param yPos      The position of the top side of the label.
     */
    public int createUiLabel(String labelText, BitmapFont labelFont, float xPos, float yPos) {
        int label = buildArchetype(uiText);

        Text labelTextComp = textComponentMapper.get(label);
        labelTextComp.text = labelText;
        labelTextComp.font = labelFont;

        Position labelPosition = positionComponentMapper.get(label);
        labelPosition.x = xPos;
        labelPosition.y = yPos;

        return label;
    }

    /**
     * @param id     The id of the entity to edit.
     * @param x      The x-cord of the sprite.
     * @param y      The y-cord of the sprite.
     * @param width  The width of the sprite.
     * @param height The height of the sprite.
     * @param image  The texture to use for the sprite.
     * @return The id of the entity.
     */
    public int editSprite(int id, float x, float y, float width, float height, Texture image) {
        Position spritePosition = positionComponentMapper.get(id);
        spritePosition.setBounds(x, y, width, height);

        Renderable spriteRenderable = renderableComponentMapper.get(id);
        spriteRenderable.texture = image;

        return id;
    }

    public int createUiSprite(float x, float y, float width, float height, Texture image) {
        int sprite = buildArchetype(uiSprite);
        editSprite(sprite, x, y, width, height, image);
        return sprite;
    }

    public int createUiButton(float x, float y, float width, float height, Texture image, Event event) {
        int button = buildArchetype(uiButton);
        editSprite(button, x, y, width, height, image);

        Touchable buttonTouchable = touchableComponentMapper.get(button);
        buttonTouchable.event = event;
        return button;
    }

    @Override
    protected void processSystem() {
    }
}
