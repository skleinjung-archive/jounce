package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BalancedAiPaddleController;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.Screen;
import com.thrashplay.luna.api.engine.ScreenManager;
import com.thrashplay.luna.renderable.ClearScreen;
import com.thrashplay.luna.android.input.TouchManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TitleScreen extends Screen {
    private Jounce jounce;
    private TouchManager touchManager;
    private ScreenManager screenManager;

    public TitleScreen(Jounce jounce) {
        // the screen and background
        entityManager.addEntity(new ClearScreen(0x333333));
        entityManager.addEntity(new GameBoard(jounce));

        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        entityManager.addEntity(leftPaddle);
        Paddle rightPaddle = new Paddle(jounce, Player.Right);
        entityManager.addEntity(rightPaddle);

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);
        entityManager.addEntity(ball);

        // paddle controllers
        entityManager.addEntity(new BallChasingPaddleController(leftPaddle, ball));
        entityManager.addEntity(new BalancedAiPaddleController(jounce, rightPaddle, ball));

        // the score
        entityManager.addEntity(new Score(jounce, ball));
        entityManager.addEntity(new TitleText(jounce));

        this.jounce = jounce;
        touchManager = jounce.getTouchManager();
        screenManager = jounce.getScreenManager();

        jounce.clearScores();
    }

    @Override
    protected void doScreenUpdate() {
        super.doScreenUpdate();
        if (touchManager.isDown()) {
            screenManager.setCurrentScreen(new GameScreen(jounce));
        }
    }
}
