package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.EntityManagerScreen;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class GameScreen extends EntityManagerScreen implements BackButtonListener {

    private Jounce jounce;
    private boolean backButtonPressed = false;

    public GameScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    public void initialize() {
        // the screen and background
        entityManager.addEntity(new ClearScreen(0x333333));
//        entityManager.addEntity(new FpsDisplay());
        entityManager.addEntity(new GameBoard(jounce));

        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        entityManager.addEntity(leftPaddle);
        Paddle rightPaddle = new Paddle(jounce, Player.Right);
        entityManager.addEntity(rightPaddle);

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);
        entityManager.addEntity(ball);
//        entityManager.addEntity(new BallTrails(ball));

        // paddle controllers
        entityManager.addEntity(new TouchPaddleController(jounce, leftPaddle));
//        entityManager.addEntity(new PerfectAiPaddleController(rightPaddle, ball));
//        entityManager.addEntity(new BalancedAiPaddleController(jounce, rightPaddle, ball));
//        entityManager.addEntity(new BehaviorBasedPaddleController(jounce, rightPaddle, ball));
        entityManager.addEntity(new BallChasingPaddleController(rightPaddle, ball));

        // the score
        entityManager.addEntity(new Score(jounce, ball));

//        entityManager.addEntity(new DebugString(jounce, ball, leftPaddle));

        jounce.getBackButtonManager().addBackButtonListener(this);

        jounce.clearScores();
        backButtonPressed = false;
    }

    @Override
    public void shutdown() {
        jounce.getBackButtonManager().removeBackButtonListener(this);
        entityManager.removeAll();
    }

    @Override
    public String getNextScreen() {
        if (backButtonPressed) {
            return "title";
        } else if (jounce.getLeftPlayerScore() > 10 || jounce.getRightPlayerScore() > 10) {
            return "victory";
        } else {
            return super.getNextScreen();
        }
    }

    @Override
    public boolean onBackButtonPressed() {
        backButtonPressed = true;
        return true;
    }
}