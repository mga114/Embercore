package animation.easing;

import animation.easing.Curve;
import animation.easing.EaseOutBounce;
import animation.easing.EaseOutQuint;

public class Easing {

    public static Curve easeOutQuint () {
        return new EaseOutQuint();
    }

    public static Curve easeOutBounce () {return new EaseOutBounce();}
}
