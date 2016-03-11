package com.thrashplay.jounce;

import com.thrashplay.jounce.entity.Player;
import com.thrashplay.luna.api.engine.ScreenManager;
import com.thrashplay.luna.android.input.TouchManager;
import com.thrashplay.luna.android.sound.SoundManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public interface Jounce {
    Player getLastPlayerToScore();

    int getLeftPlayerScore();

    void addPointForLeftPlayer();

    int getRightPlayerScore();

    void addPointForRightPlayer();

    void clearScores();

    TouchManager getTouchManager();

    SoundManager getSoundManager();

    Rectangle getGameBoardDimensions();

    ScreenManager getScreenManager();

    float getWidthScalar();

    float getHeightScalar();
}


