package com.thrashplay.jounce;

import com.thrashplay.jounce.entity.Player;
import com.thrashplay.jounce.screen.JounceScreenManager;
import com.thrashplay.luna.android.engine.LunaGame;
import com.thrashplay.luna.api.engine.Luna;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.input.BackButtonManager;
import com.thrashplay.luna.engine.LunaGameConfig;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MainActivity extends LunaGame implements Jounce {

    private Player lastPlayerToScore = null;
    private int leftPlayerScore = 0;
    private int rightPlayerScore = 0;

    @Override
    protected LunaGameConfig getGameConfig(Luna luna) {
        LunaGameConfig config = new LunaGameConfig();
        config.setSceneDimensions(480, 320);
        config.setScreenManager(new JounceScreenManager(this));
        config.setDefaultScreen("menu");
        return config;
    }

    @Override
    protected int getSceneWidth() {
        return 480;
    }

    @Override
    protected int getSceneHeight() {
        return 320;
    }

    @Override
    public BackButtonManager getBackButtonManager() {
        return this;
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
        return new Rectangle(0, 0, getSceneWidth(), getSceneHeight());
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
