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
        super();
        registerScreen("game", new GameScreen(jounce));
        registerScreen("match", new MatchScreen(jounce));
        registerScreen("victory", new VictoryScreen(jounce));
        registerScreen("menu", new MenuScreen(jounce));
    }
}
