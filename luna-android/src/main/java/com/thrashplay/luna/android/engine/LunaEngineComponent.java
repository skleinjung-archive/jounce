package com.thrashplay.luna.android.engine;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public interface LunaEngineComponent {
    /**
     * Notifies this engine component that the game has been paused.
     */
    public void pause();

    /**
     * Notifies this engine component that the game has been resumed.
     */
    public void resume();
}
