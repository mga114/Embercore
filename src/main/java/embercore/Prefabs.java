package embercore;

import components.Sprite;
import components.SpriteRenderer;
import org.joml.Vector2f;

public class Prefabs {

    public static GameObject generateSpriteObject (Sprite sprite, int sizeX, int sizeY) {
        GameObject obj = new GameObject ("Test Object", new Transform(new Vector2f(), new Vector2f(sizeX, sizeY)), 0);
        SpriteRenderer renderer = new SpriteRenderer (sprite);
        obj.addComponent(renderer);
        return obj;
    }

    public static GameObject generateSpriteObject (Sprite sprite, Transform transform, int sizeX, int sizeY) {
        GameObject obj = new GameObject ("Test Object", transform, 0);
        SpriteRenderer renderer = new SpriteRenderer (sprite);
        obj.addComponent(renderer);
        return obj;
    }

}
