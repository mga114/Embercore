package animation;

import animation.easing.Curve;
import animation.easing.Easing;

import java.lang.reflect.Method;

public class Transition {
    public Curve xDriverMethod;
    public Method yDriverMethod;
    public Method alphaDriverMethod;
    public float xDelta;
    public float yDelta;
    public float hDelta;
    public float wDelta;
    public float alphaDelta;

    public Transition () {
        this.xDriverMethod = Easing.easeOutQuint();
    }

    public static Transition newTransition () {
        return new Transition();
    }

    public Transition xDriver (Curve xDriver, float delta) {
        this.xDriverMethod = xDriver;
        this.xDelta = delta;
        return this;
    }

    public Transition yDriver (Method yDriver, float delta) {
        this.yDriverMethod = yDriver;
        this.yDelta = delta;
        return this;
    }

    public Transition alphaDriver (Method alphaDriver, float delta) {
        this.alphaDriverMethod = alphaDriver;
        this.alphaDelta = delta;
        return this;
    }
}
