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
public class VariableFpsMainLoop extends AbstractMainLoop {

    public VariableFpsMainLoop(ScreenManager screenManager, GraphicsManager graphicsManager) {
        super(screenManager, graphicsManager);
    }

    @Override
    public void run() {
        Timing timing = new Timing();
        timing.reset();

        while (running) {
            long elapsed = timing.elapsedAs(TimeUnit.MILLISECONDS);

            Graphics g = graphicsManager.beginFrame();
            if (g != null) {
                timing.reset();

                if (screenManager.getCurrentScreen() != null) {
                    screenManager.getCurrentScreen().update(g);//, elapsed);
                    screenManager.getCurrentScreen().render(g);
                }
            }
            graphicsManager.endFrame();
        }
    }
}
