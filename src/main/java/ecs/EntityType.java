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

    private static boolean hasCreatedAny = false;

    private static final ArrayList<EntityType> entityTypes = new ArrayList<>();

    private static final ArrayList<Entity> addBuffer = new ArrayList<>();
    private static final ArrayList<Entity> removeBuffer = new ArrayList<>();

    public EntityType add(Class<? extends Component> component) {
        if (hasCreated) {
            throw new RuntimeException("Cannot add new components after the entity type has been created!");
        }
        components.add(component);
        groups = Group.getGroups(this);
        entityTypes.add(this);
        return this;
    }

    public EntityType add(Class<? extends Component> component, Component defaultVal) {
        add(component);
        defaultValues.put(component, defaultVal);
        return this;
    }

    public ArrayList<Class<? extends Component>> getComponents() {
        return components;
    }

    public HashMap<Class<? extends Component>, Component> getDefaultValues() {
        return defaultValues;
    }

    public Entity create() {
        hasCreatedAny = true;
        hasCreated = true;
        Entity e = new Entity(this);

        addBuffer.add(e);
        return e;
    }

    public void delete(Entity e) {
        removeBuffer.add(e);
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

    public static void updateEntityTypes(Group g) {
        /*
         * This method should be called when a new group is created.
         */
        if (hasCreatedAny) {
            throw new RuntimeException("Cannot create groups after Entities have been created!");
        }

        for (EntityType etype: entityTypes) {
            if (g.shouldAccept(etype)) {
                etype.groups.add(g);
            }
        }
    }
}


