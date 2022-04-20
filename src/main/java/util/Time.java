package util;

public class Time {
    public static float timeStarted = System.nanoTime();

    public static double getTime () { return (double) ((System.nanoTime() - timeStarted) * 1E-9);}
}
