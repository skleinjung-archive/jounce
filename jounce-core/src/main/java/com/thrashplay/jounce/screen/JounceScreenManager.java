package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.engine.DefaultScreenManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class JounceScreenManager extends DefaultScreenManager {
    public JounceScreenManager(Jounce jounce) {
        TitleScreen titleScreen = new TitleScreen(jounce);
        registerScreen("title", titleScreen);
        registerScreen("game", new GameScreen(jounce));
    }
}
