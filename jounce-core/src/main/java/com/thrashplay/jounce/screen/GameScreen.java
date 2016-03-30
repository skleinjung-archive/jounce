package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.engine.LegacyGameObjectAdapter;
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
        gameObjectManager.register(new LegacyGameObjectAdapter(new ClearScreen(0x333333)));
//        entityManager.addEntity(new FpsDisplay());
        gameObjectManager.register(new LegacyGameObjectAdapter(new GameBoard(jounce)));

        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        gameObjectManager.register(new LegacyGameObjectAdapter(leftPaddle));
        Paddle rightPaddle = new Paddle(jounce, Player.Right);
        gameObjectManager.register(new LegacyGameObjectAdapter(rightPaddle));

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);
        gameObjectManager.register(new LegacyGameObjectAdapter(ball));
//        entityManager.addEntity(new BallTrails(ball));

        // paddle controllers
//        entityManager.addEntity(new TouchPaddleController(jounce, leftPaddle));
        gameObjectManager.register(new LegacyGameObjectAdapter(new BallChasingPaddleController(rightPaddle, ball)));

        // the score
        gameObjectManager.register(new LegacyGameObjectAdapter(new ScoreDisplay(jounce)));
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
