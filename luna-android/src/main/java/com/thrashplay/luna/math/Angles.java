package com.thrashplay.luna.math;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Angles {
    public static double toRadians(double degrees) {
        return degrees * (Math.PI / 180D);
    }

    public static double toDegrees(double radians) {
        return radians * (180D / Math.PI);
    }

    /**
     * Returns an equivalent angle, in degrees, where 0 <= result <= 360.
     * @param angle the angle to normalize
     * @return the normalized angle
     */
    public static float normalize(float angle) {
        while (angle < 0) {
            angle += 360;
        }
        while (angle > 360) {
            angle -= 360;
        }
        return angle;
    }
}
