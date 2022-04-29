package gui;

import ecs.Entity;
import ecs.Transform;
import org.joml.Vector2f;

import java.util.Objects;

public class Animation {
    protected Transition transition;
    protected float duration;
    protected float delay;
    protected float currentTime;
    private Entity ent;
    private final Transform currentTransform;
    private final Vector2f originalPosition;
    private final Vector2f originalScale;
    public String name;
    public boolean active = false;

    public Animation (String name, Transition transition, float duration, float delay, Entity ent) {
        this.transition = transition;
        this.duration = duration;
        this.delay = delay;
        this.ent = ent;
        this.currentTime = 0.0f;
        this.name = name;
        this.currentTransform = ent.get(Transform.class);
        this.originalPosition = new Vector2f();
        this.originalScale = new Vector2f();
    }

    public void startAnimation() {
        active = true;
        originalPosition.set(currentTransform.position);
        originalScale.set(currentTransform.scale);
        currentTime = 0.0f;
        AnimationSystem.animations.add(this);
    }

    public void update (float dt) {
        this.currentTime += dt;
        if (currentTime >= delay && currentTime < duration + delay) {
            float d = (currentTime - delay) / duration;
            if (transition.xDriverMethod != null) {
                currentTransform.position.x = originalPosition.x + ((float) transition.xDriverMethod.curve(d) * transition.xDelta);
            }
        } else if (currentTime >= delay) {
            cancelAnimation();
        }
    }

    public void cancelAnimation() {
        AnimationSystem.animations.remove(this);
        active = false;
        currentTransform.position.set(new Vector2f(originalPosition.x + transition.xDelta, originalPosition.y + transition.yDelta));
        currentTransform.scale.set(new Vector2f(originalScale.x + transition.wDelta, originalScale.y + transition.hDelta));
    }

    @Override
    public boolean equals (Object other) {
        if (other == null) return false;
        if (!(other instanceof Animation)) return false;
        if (other == this) return true;
        return Objects.equals(this.name, ((Animation) other).name) && this.ent == ((Animation) other).ent;
    }
}
