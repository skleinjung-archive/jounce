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
        registerScreen("title", new TitleScreen(jounce));
        registerScreen("game", new GameScreen(jounce));
        registerScreen("victory", new VictoryScreen(jounce));
    }
}
