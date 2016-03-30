package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.engine.LegacyGameObjectAdapter;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class VictoryScreen extends DefaultScreen implements BackButtonListener {

    private Jounce jounce;
    private boolean backButtonPressed;

    public VictoryScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        // the screen and background
        gameObjectManager.register(new LegacyGameObjectAdapter(new ClearScreen(0x333333)));
//        entityManager.addEntity(new FpsDisplay());
        GameBoard gameBoard = new GameBoard(jounce);
        gameBoard.setDrawCenterStripe(false);
        gameObjectManager.register(new LegacyGameObjectAdapter(gameBoard));


        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        Paddle rightPaddle = new Paddle(jounce, Player.Right);

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);

        // the score
        gameObjectManager.register(new LegacyGameObjectAdapter(new ScoreDisplay(jounce)));

        // the victory text
        gameObjectManager.register(new LegacyGameObjectAdapter(new VictoryText(jounce)));

        backButtonPressed = false;
        jounce.getBackButtonManager().addBackButtonListener(this);
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
