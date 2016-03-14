package com.thrashplay.jounce.desktop;

import com.thrashplay.jounce.screen.JounceScreenManager;
import com.thrashplay.luna.api.engine.Luna;
import com.thrashplay.luna.desktop.LunaCanvas;
import com.thrashplay.luna.desktop.LunaWindow;
import com.thrashplay.luna.engine.LunaGameConfig;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Main {
    public static void main(String[] args) {
        new LunaWindow("Jounce", 760, 480) {
            @Override
            protected LunaGameConfig createGameConfig(LunaCanvas canvas, Luna luna) {
                LunaGameConfig gameConfig = new LunaGameConfig();
                gameConfig.setScreenManager(new JounceScreenManager(new DesktopJounce(canvas)));
                gameConfig.setDefaultScreen("title");
                return gameConfig;
            }
        };
    }
}
