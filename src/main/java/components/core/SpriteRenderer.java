package components.core;

import ecs.Component;
import ecs.Entity;
import embercore.Window;
import events.EventID;
import events.Events;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;
    public Entity entity;
    public int zIndex;

    private Transform lastTransform;
    private boolean isDirty = false;

    public SpriteRenderer (Vector4f color, Entity entity, int zIndex) {
        this.color = color;
        this.sprite = new Sprite(null);
        this.zIndex = zIndex;
        init (entity);
    }

    public SpriteRenderer(Sprite sprite, Entity entity, int zIndex) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
        this.zIndex = zIndex;
        init (entity);
    }

    private void init (Entity entity) {
        this.entity = entity;
        this.isDirty = true;

        Transform t = entity.get(Transform.class);
        this.lastTransform = t.copy();

        Events.on(EventID.UPDATE, (dt) -> {
            if (!this.lastTransform.equals(t)) {
                t.copy(this.lastTransform);
                isDirty = true;
            }
        });

        //Window.getScene().renderer.add(this);
    }

    public Vector4f getColor () {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean () {
        this.isDirty = false;
    }
}
