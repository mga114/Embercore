package gui;

import gui.easing.Curve;
import gui.easing.EaseOutQuint;

public class Easing {

    public static Curve easeOutQuint () {
        return new EaseOutQuint();
    }
}
