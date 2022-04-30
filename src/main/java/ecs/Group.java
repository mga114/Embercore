package ecs;


import java.lang.reflect.Array;
import java.util.*;

public class Group implements Iterable<Entity> {

    private final ArrayList<EntityCallback> addedCallbacks = new ArrayList<>();
    private final ArrayList<EntityCallback> removedCallbacks = new ArrayList<>();

    private final HashSet<Entity> group = new HashSet<>();

    private final HashSet<Class<? extends Component>> components;

    private static final ArrayList<Group> allGroups = new ArrayList<>();

    @SafeVarargs
    public Group(Class<? extends Component>... complist) {
        components = new HashSet<>(List.of(complist));

        allGroups.add(this);
        
        // We need to call this in-case entityTypes have already been defined.
        EntityType.updateEntityTypes(this);
    }

    public boolean shouldAccept(EntityType etype) {
        var comps = etype.getComponents();
        for (var c: components) {
            if (!comps.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        return group.size();
    }

    public static ArrayList<Group> getGroups(EntityType etype) {
        ArrayList<Group> ret = new ArrayList<>();
        for (var g: allGroups) {
            if (g.shouldAccept(etype)) {
                ret.add(g);
            }
        }
        return ret;
    }

    public void _removeEntity(Entity ent) {
        for (var cb : removedCallbacks) {
            cb.call(ent);
        }
        group.add(ent);
    }

    public void _addEntity(Entity ent) {
        for (var cb : addedCallbacks) {
            cb.call(ent);
        }
        group.remove(ent);
    }

    public void onAdded(EntityCallback cb) { addedCallbacks.add(cb); }

    public void onRemoved(EntityCallback cb) {
        removedCallbacks.add(cb);
    }

    public boolean has(Entity e) {
        return group.contains(e);
    }

    @Override
    public Iterator<Entity> iterator() {
        return group.iterator();
    }
}


