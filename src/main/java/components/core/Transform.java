package components.core;

import ecs.Component;
import org.joml.Vector2f;

public class Transform extends Component {
    public Vector2f position;
    public Vector2f scale;

    public Transform (Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Transform copy () {
        return new Transform (new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy (Transform transform) {
        transform.position.set(this.position);
        transform.scale.set(this.scale);
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform)) return false;
        if (o == this) return true;
        Transform other = (Transform) o;
        return other.position.x == this.position.x && other.position.y == this.position.y &&
                other.scale.x == this.scale.x && this.scale.y == other.scale.y;
    }

    @Override
    public String toString() {
        return "X: " + position.x + " | Y: " + position.y + " | W: " + scale.x + " | H: " + scale.y;
    }
}
