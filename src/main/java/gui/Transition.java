package gui;

import gui.easing.Curve;

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

    public void xDriver (Curve xDriver, float delta) {
        this.xDriverMethod = xDriver;
        this.xDelta = delta;
    }

    public void yDriver (Method yDriver, float delta) {
        this.yDriverMethod = yDriver;
        this.yDelta = delta;
    }

    public void alphaDriver (Method alphaDriver, float delta) {
        this.alphaDriverMethod = alphaDriver;
        this.alphaDelta = delta;
    }
}
