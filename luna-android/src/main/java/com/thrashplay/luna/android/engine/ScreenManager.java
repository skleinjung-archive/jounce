package com.thrashplay.luna.android.engine;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class ScreenManager {
    private Screen currentScreen;

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }
}
