package animation.easing;

public class EaseOutQuint implements Curve{
    @Override
    public double curve(double x) {
        return 1.0 - Math.pow(1.0 - x, 5);
    }
}
