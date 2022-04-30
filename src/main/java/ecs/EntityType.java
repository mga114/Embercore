package ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class EntityType {

    private ArrayList<Group> groups;
    private final ArrayList<Class<? extends Component>> components = new ArrayList<>();

    private final HashMap<Class<? extends Component>, Component> defaultValues = new HashMap<>();

    private boolean hasCreated = false;

    private static final ArrayList<Entity> addBuffer = new ArrayList<>();
    private static final ArrayList<Entity> removeBuffer = new ArrayList<>();

    public void add(Class<? extends Component> component) {
        if (hasCreated) {
            throw new RuntimeException("Cannot add new components after the entity type has been created!");
        }
        components.add(component);
        groups = Group.getGroups(this);
    }

    public void add(Class<? extends Component> component, Component defaultVal) {
        add(component);
        defaultValues.put(component, defaultVal);
    }

    public ArrayList<Class<? extends Component>> getComponents() {
        return components;
    }

    public HashMap<Class<? extends Component>, Component> getDefaultValues() {
        return defaultValues;
    }

    public Entity create() {
        hasCreated = true;
        Entity e = new Entity(this);

        // TODO: Add a flush buffer for this. It's a terrible idea to create entities willy-nilly inbetween frames.
        for (var g: groups) {
            g._addEntity(e);
        }
        return e;
    }

    public void delete(Entity e) {
        // TODO: Add a flush buffer for this too. Deleting ents between frames is bad
        for (Group g: groups) {
            g._removeEntity(e);
        }
    }

    public static void flush() {
        for (Entity e: removeBuffer) {
            EntityType etype = e.getType();
            for (Group g : etype.groups) {
                g._removeEntity(e);
            }
        }

        for (Entity e: addBuffer) {
            EntityType etype = e.getType();
            for (Group g: etype.groups) {
                g._addEntity(e);
            }
        }
    }
}


