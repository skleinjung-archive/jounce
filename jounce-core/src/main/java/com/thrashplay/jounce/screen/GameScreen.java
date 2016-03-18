package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class GameScreen extends DefaultScreen implements BackButtonListener {

    private Jounce jounce;
    private boolean backButtonPressed = false;

    public GameScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        // the screen and background
        gameObjectManager.addEntity(new ClearScreen(0x333333));
//        entityManager.addEntity(new FpsDisplay());
        gameObjectManager.addEntity(new GameBoard(jounce));

        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        gameObjectManager.addEntity(leftPaddle);
        Paddle rightPaddle = new Paddle(jounce, Player.Right);
        gameObjectManager.addEntity(rightPaddle);

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);
        gameObjectManager.addEntity(ball);
//        entityManager.addEntity(new BallTrails(ball));

        // paddle controllers
//        entityManager.addEntity(new TouchPaddleController(jounce, leftPaddle));
        gameObjectManager.addEntity(new BallChasingPaddleController(rightPaddle, ball));

        // the score
        gameObjectManager.addEntity(new ScoreDisplay(jounce));
        //entityManager.addEntity(new ScoreBehavior(jounce, ball));

//        entityManager.addEntity(new DebugString(jounce, ball, leftPaddle));

        jounce.getBackButtonManager().addBackButtonListener(this);

        jounce.clearScores();
        backButtonPressed = false;
    }

    @Override
    public void shutdown() {
        jounce.getBackButtonManager().removeBackButtonListener(this);
        gameObjectManager.unregisterAll();
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
