package com.thrashplay.luna.math;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Random {
    public static int getInteger(int upperBoundExclusive) {
        return (int) (Math.random() * upperBoundExclusive);
    }

    public static int getInteger(int lowerBoundInclusive, int upperBoundExclusive) {
        return getInteger(upperBoundExclusive - lowerBoundInclusive) + lowerBoundInclusive;
    }
}
