package com.thrashplay.jounce.desktop;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.Player;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.api.input.BackButtonManager;
import com.thrashplay.luna.api.input.MultiTouchManager;
import com.thrashplay.luna.api.input.TouchManager;
import com.thrashplay.luna.api.sound.SoundManager;
import com.thrashplay.luna.desktop.LunaCanvas;
import com.thrashplay.luna.desktop.input.DesktopMultiTouchManager;
import com.thrashplay.luna.desktop.input.MouseTouchManager;
import com.thrashplay.luna.desktop.sound.DesktopSoundManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class DesktopJounce implements Jounce {
    private Player lastPlayerToScore = null;
    private int leftPlayerScore = 0;
    private int rightPlayerScore = 0;

    private LunaCanvas canvas;
    private TouchManager touchManager;
    private MultiTouchManager multiTouchManager;
    private SoundManager soundManager;

    public DesktopJounce(LunaCanvas canvas) {
        this.canvas = canvas;
        this.touchManager = new MouseTouchManager(canvas, getGameBoardDimensions().getWidth(), getGameBoardDimensions().getHeight());
        this.multiTouchManager = new DesktopMultiTouchManager(touchManager);
        this.soundManager = new DesktopSoundManager();
    }

    @Override
    public TouchManager getTouchManager() {
        return touchManager;
    }

    @Override
    public MultiTouchManager getMultiTouchManager() {
        return multiTouchManager;
    }

    @Override
    public SoundManager getSoundManager() {
        return soundManager;
    }

    @Override
    public BackButtonManager getBackButtonManager() {
        // noop, since there is no back button on desktop versions - todo, maybe implement this with escape
        return new BackButtonManager() {
            @Override
            public void addBackButtonListener(BackButtonListener listener) {

            }

            @Override
            public void removeBackButtonListener(BackButtonListener listener) {

            }
        };
    }

    @Override
    public Player getLastPlayerToScore() {
        return lastPlayerToScore;
    }

    @Override
    public int getLeftPlayerScore() {
        return leftPlayerScore;
    }

    @Override
    public void addPointForLeftPlayer() {
        leftPlayerScore++;
        lastPlayerToScore = Player.Left;
    }

    @Override
    public int getRightPlayerScore() {
        return rightPlayerScore;
    }

    @Override
    public void addPointForRightPlayer() {
        rightPlayerScore++;
        lastPlayerToScore = Player.Right;
    }

    public void clearScores() {
        rightPlayerScore = leftPlayerScore = 0;
        lastPlayerToScore = null;
    }

    @Override
    public Rectangle getGameBoardDimensions() {
        return new Rectangle(0, 0, 480, 320);
    }

    @Override
    public float getWidthScalar() {
        return getGameBoardDimensions().getWidth() / 959f;
    }

    @Override
    public float getHeightScalar() {
        return getGameBoardDimensions().getHeight() / 719f;
    }
}
