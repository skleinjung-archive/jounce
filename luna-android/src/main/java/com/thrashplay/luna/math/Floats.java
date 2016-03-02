package com.thrashplay.luna.math;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Floats {
    public static boolean areApproximatelyEqual(float f1, float f2) {
        return areApproximatelyEqual(f1, f2, 0.001f);
    }

    public static boolean areApproximatelyEqual(float f1, float f2, float tolerance) {
        return Math.abs(f1 - f2) <= tolerance;
    }
}
