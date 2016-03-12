package com.thrashplay.jounce;

import com.thrashplay.jounce.entity.Player;
import com.thrashplay.luna.api.input.TouchManager;
import com.thrashplay.luna.api.sound.SoundManager;

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

    float getWidthScalar();

    float getHeightScalar();
}


