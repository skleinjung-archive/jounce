package com.thrashplay.luna.android.graphics.renderable;

import com.thrashplay.luna.android.engine.loop.Timing;
import com.thrashplay.luna.android.engine.Updateable;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

import java.util.concurrent.TimeUnit;

/**
 * Drawable used to render the game's FPS.
 *
 * @author Sean Kleinjung
 */
public class FpsDisplay implements Renderable, Updateable {

    // how often to update the FPS count, in milliseconds
    private int updateInterval;

    // the number of frames elapsed since the FPS was last updated
    private int frames = 0;

    // timer used to keep track of FPS update intervals
    private Timing timing = new Timing();

    // the current FPS
    private double fps = 0;

    public FpsDisplay() {
        this(1000);
    }

    public FpsDisplay(int updateInterval) {
        this.updateInterval = updateInterval;
        timing.reset();
    }

    @Override
    public void update(Graphics g) {
        long elapsed = timing.elapsedAs(TimeUnit.MILLISECONDS);
        if (elapsed >= updateInterval) {
            fps = Math.round(((double) frames / elapsed) * 1000 * 10) / 10D;
            frames = 0;
            timing.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        frames++;
        g.drawRect(0, g.getHeight() - 50, 100, 50, 0xff000000);
        g.drawString("FPS: " + fps, 10, g.getHeight() - 20, 0xffffffff);
    }
}
