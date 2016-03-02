package com.thrashplay.luna.android.engine.loop;

import com.thrashplay.luna.android.engine.ScreenManager;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.GraphicsManager;

import java.util.concurrent.TimeUnit;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class FixedFpsMainLoop extends AbstractMainLoop {
    private int targetFramesPerSecond;

    public FixedFpsMainLoop(ScreenManager screenManager, GraphicsManager graphicsManager) {
        this(screenManager, graphicsManager, 60);
    }

    public FixedFpsMainLoop(ScreenManager screenManager, GraphicsManager graphicsManager, int targetFramesPerSecond) {
        super(screenManager, graphicsManager);
        this.targetFramesPerSecond = targetFramesPerSecond;
    }

    public void run() {
        Timing timing = new Timing();
        timing.reset();

        long elapsed = 0;
        long frameDuration = 1000 / targetFramesPerSecond;

        while (running) {
            elapsed += timing.elapsedAs(TimeUnit.MILLISECONDS);
            timing.reset();

            Graphics g = graphicsManager.beginFrame();
            if (g != null) {
                while (elapsed >= frameDuration) {
                    if (screenManager.getCurrentScreen() != null) {
                        screenManager.getCurrentScreen().update(g);
                    }
                    elapsed -= frameDuration;
                }

                if (screenManager.getCurrentScreen() != null) {
                    screenManager.getCurrentScreen().render(g);
                }
            }
            graphicsManager.endFrame();
        }
    }
}
