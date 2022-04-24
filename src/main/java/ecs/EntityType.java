package ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class EntityType {

    private ArrayList<Group> groups;
    private ArrayList<Class<? extends Component>> components;

    private HashMap<Class<? extends Component>, Component> defaultValues;

    private boolean hasCreated = false;

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
        for (var g: groups) {
            g._removeEntity(e);
        }
    }
}




