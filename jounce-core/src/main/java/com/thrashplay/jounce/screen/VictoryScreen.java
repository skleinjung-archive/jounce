package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.luna.api.engine.EntityManagerScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class VictoryScreen extends EntityManagerScreen implements BackButtonListener {

    private Jounce jounce;
    private boolean backButtonPressed;

    public VictoryScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    public void initialize(Rectangle screenBounds) {
        // the screen and background
        entityManager.addEntity(new ClearScreen(0x333333));
//        entityManager.addEntity(new FpsDisplay());
        GameBoard gameBoard = new GameBoard(jounce);
        gameBoard.setDrawCenterStripe(false);
        entityManager.addEntity(gameBoard);


        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        Paddle rightPaddle = new Paddle(jounce, Player.Right);

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);

        // the score
        entityManager.addEntity(new ScoreDisplay(jounce, ball));

        // the victory text
        entityManager.addEntity(new VictoryText(jounce));

        backButtonPressed = false;
        jounce.getBackButtonManager().addBackButtonListener(this);
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
