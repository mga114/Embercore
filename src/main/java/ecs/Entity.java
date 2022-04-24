package ecs;


import java.util.HashMap;
import java.util.Objects;

public class Entity {
    private final HashMap<Class<? extends Component>, Component> fields = new HashMap<>();
    private final EntityType entityType;

    public Entity(EntityType e) {
        entityType = e;
    }

    public EntityType getType() {
        return entityType;
    }

    public <T extends Component> T get(Class<? extends Component> comp) {
        T ret = (T) fields.get(comp);
        if (Objects.isNull(ret)) {
            ret = (T) entityType.getDefaultValues().get(comp);
        }
        return ret;
    }

    public <T extends Component> void set(Class<? extends Component> comp, T val) {
        fields.put(comp, val);
    }
}


