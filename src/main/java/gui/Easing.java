package gui;

public class Easing {

    public static double easeOutQuint (double x) {
        return 1.0 - Math.pow(1.0 - x, 5);
    }
}
