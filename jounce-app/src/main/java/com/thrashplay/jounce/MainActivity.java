package com.thrashplay.jounce;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import com.thrashplay.jounce.entity.Player;
import com.thrashplay.jounce.screen.JounceScreenManager;
import com.thrashplay.luna.android.engine.LunaGame;
import com.thrashplay.luna.android.graphics.LunaSurfaceView;
import com.thrashplay.luna.android.input.TouchManager;
import com.thrashplay.luna.android.sound.SoundManager;
import com.thrashplay.luna.api.engine.ScreenManager;
import com.thrashplay.luna.math.Floats;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MainActivity extends LunaGame implements Jounce {

    private Rectangle gameBoardDimensions;
    private TouchManager touchManager;
    private SoundManager soundManager;

    private Player lastPlayerToScore = null;
    private int leftPlayerScore = 0;
    private int rightPlayerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                getScreenManager().setCurrentScreen("title");
            }
        });

        touchManager = new TouchManager(getSurfaceView());
        soundManager = new SoundManager(this);
    }

    @Override
    protected ScreenManager createScreenManager() {
        return new JounceScreenManager(this);
    }

    @Override
    public TouchManager getTouchManager() {
        return touchManager;
    }

    @Override
    public SoundManager getSoundManager() {
        return soundManager;
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
        LunaSurfaceView view = getSurfaceView();

        float aspectRatio = (float) view.getWidth() / (float) view.getHeight();
        float fourByThree = 4f / 3f;
        int width = 0;
        int height = 0;
        if (Floats.areApproximatelyEqual(aspectRatio, fourByThree)) {
            width = view.getWidth();
            height = view.getHeight();
        } else if (aspectRatio > fourByThree) {
            height = view.getHeight();
            width = (int) (height * fourByThree);
        } else if (aspectRatio < fourByThree) {
            width = view.getWidth();
            height = (int) (width / fourByThree);
        }

        gameBoardDimensions = new Rectangle((view.getWidth() - width) / 2, (view.getHeight() - height) / 2, width, height);
        return gameBoardDimensions;
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
