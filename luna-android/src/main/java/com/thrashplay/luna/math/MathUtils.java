package com.thrashplay.luna.math;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MathUtils {
    public static int sign(double value) {
        if (value < 0) {
            return -1;
        } else if (value > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
