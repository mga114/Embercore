package embercore;

import components.Sprite;
import components.SpriteRenderer;
import ecs.Entity;
import ecs.EntityType;
import ecs.Transform;
import gui.Animator;
import org.joml.Vector2f;

public class Prefabs {

    public static Entity generateSpriteEntity (Sprite sprite) {
        EntityType type = new EntityType();
        type.add(Transform.class);
        type.add(Animator.class);
        type.add(SpriteRenderer.class);

        Entity e = type.create();
        e.set(Transform.class, new Transform(new Vector2f(400.0f, 100.0f), new Vector2f(100.0f, 100.0f)));
        e.set(SpriteRenderer.class, new SpriteRenderer(sprite, e, 1));
        return e;
    }

}
