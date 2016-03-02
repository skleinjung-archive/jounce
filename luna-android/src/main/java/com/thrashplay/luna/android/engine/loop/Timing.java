package com.thrashplay.luna.android.engine.loop;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Timing {
    private long start = 0;

    public boolean isStarted() {
        return start != 0;
    }

    public void reset() {
        start = System.nanoTime();
    }

    public long elapsed() {
        return System.nanoTime() - start;
    }

    public long elapsedAs(TimeUnit timeUnit) {
        return timeUnit.convert(elapsed(), TimeUnit.NANOSECONDS);
    }
}
