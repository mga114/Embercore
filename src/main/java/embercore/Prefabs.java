package embercore;

import components.core.Sprite;
import components.core.SpriteRenderer;
import ecs.Entity;
import ecs.EntityType;
import components.core.Transform;
import animation.Animator;
import org.joml.Vector2f;

public class Prefabs {

    public static EntityType entitySpriteType;

    private static void generateEntitySpriteType () {
        if (entitySpriteType == null) {
            entitySpriteType = new EntityType();
            entitySpriteType.add(Transform.class);
            entitySpriteType.add(Animator.class);
            entitySpriteType.add(SpriteRenderer.class);

        }
    }

    public static Entity generateSpriteEntity (Sprite sprite) {
        EntityType type = new EntityType();
        type.add(Transform.class);
        type.add(Animator.class);
        type.add(SpriteRenderer.class);

        Entity e = type.create();
        e.set(Transform.class, new Transform(new Vector2f(800.0f, 100.0f), new Vector2f(100.0f, 100.0f)));
        e.set(SpriteRenderer.class, new SpriteRenderer(sprite, e, 1));
        e.set(Animator.class, new Animator());
        return e;
    }

    public static Entity generateSpriteEntity (Sprite sprite, Transform transform) {
        generateEntitySpriteType();

        Entity e = entitySpriteType.create();
        e.set(Transform.class, transform);
        e.set(SpriteRenderer.class, new SpriteRenderer(sprite, e, 1));
        e.set(Animator.class, new Animator());
        return e;
    }

}
