
# ECS

## Components:
Components are just data.

```java
import ecs.Component;

class Position extends Component {
    public float x;
    public float y;
}


class Velocity extends Component {
    public float x;
    public float y;
}

```

## Entities:
Entities hold components.

To create an entity, we must create an `EntityType` first.
eg:
```java
EntityType etype = new EntityType();

// This entity type has a Position comp and a Velocity comp.
etype.add(Position.class);
etype.add(Velocity.class);

```
Now, creating an entity from an EntityType:
```java
Entity e = etype.create();

// Components must be set manually.

        
// Get component:
Position pos = e.<Position>get(Position.class);
// (returns `null` if it isn't set, or if it doesn't have the component.)
        
        
// Set component:
var position = new Position();
position.x = 10.0; position.y = 5.0;
e.<Position>set(Position.class, position);



```

## Groups:
This is the "System" part of the ECS.
```java

Group g = new Group(Position.class, Velocity.class);
// ^^^ This group has all entities that have a position, and velocity.

```



============================================================================
## But.. how should we actually use groups?
Example below.

This is an example of a fully-fledged class that handles the updating
of entity positions, via a velocity component.

```java

import events.EventID;
import events.Events;

class VelocitySystem() {

    private final Group group;

    public static setup() {
        group = new Group(Position.class, Velocity.class);

        Events.on(EventID.UPDATE, () -> {
            float dt = util.getDeltaTime();
            
            for (Entity e: group) {
               Position pos = e.<Position>get(Position.class);
               Velocity vel = e.<Velocity>get(Velocity.class);
               pos.x += vel.x * dt;
               pos.y += vel.y * dt;
            }
        });
    }
}
```
This works nicely, because any entities that have a Position component, but
no Velocity component, won't be added to the group, and therefore won't
waste processing time.




