package com.thrashplay.luna.android.engine.loop;

import com.thrashplay.luna.android.engine.ScreenManager;
import com.thrashplay.luna.android.engine.LunaEngineComponent;
import com.thrashplay.luna.android.engine.LunaGame;
import com.thrashplay.luna.android.graphics.GraphicsManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public abstract class AbstractMainLoop implements Runnable, LunaEngineComponent {
    protected volatile boolean running = false;
    protected ScreenManager screenManager;
    protected GraphicsManager graphicsManager;
    private LunaGame game;
    private Thread gameLoopThread = null;

    public AbstractMainLoop(ScreenManager screenManager, GraphicsManager graphicsManager) {
        this.screenManager = screenManager;
        this.graphicsManager = graphicsManager;
    }

    @Override
    public void pause() {
        System.out.println("Pausing thread...");

        running = false;
        while (true) {
            try {
                gameLoopThread.join();
                break;
            } catch (InterruptedException e) {
                // ignore and retry
            }
        }
    }

    @Override
    public void resume() {
        System.out.println("Starting thread...");

        running = true;
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
    }
}
