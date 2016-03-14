package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BalancedAiPaddleController;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.EntityManagerScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TitleScreen extends EntityManagerScreen {
    private Jounce jounce;

    public TitleScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void initialize(Rectangle screenBounds) {
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

        jounce.clearScores();
    }

    @Override
    public void shutdown() {
        entityManager.removeAll();
    }

    @Override
    public String getNextScreen() {
        if (jounce.getTouchManager().isDown()) {
            return "game";
        } else {
            return super.getNextScreen();
        }
    }
}
