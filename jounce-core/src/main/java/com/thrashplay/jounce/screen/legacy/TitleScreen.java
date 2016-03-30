package com.thrashplay.jounce.screen.legacy;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.*;
import com.thrashplay.jounce.entity.ai.BalancedAiPaddleController;
import com.thrashplay.jounce.entity.ai.BallChasingPaddleController;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.ui.Button;
import com.thrashplay.luna.api.ui.ButtonAdapter;
import com.thrashplay.luna.engine.LegacyGameObjectAdapter;
import com.thrashplay.luna.renderable.ClearScreen;
import com.thrashplay.luna.ui.TextButton;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TitleScreen extends DefaultScreen {
    private Jounce jounce;
    private boolean newGamePressed;

    public TitleScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        Rectangle screenBounds = new Rectangle(0, 0, 480, 320);

        // the screen and background
        gameObjectManager.register(new LegacyGameObjectAdapter(new ClearScreen(0x333333)));
        GameBoard gameBoard = new GameBoard(jounce);
        gameBoard.setDrawCenterStripe(false);
        gameObjectManager.register(new LegacyGameObjectAdapter(gameBoard));

        // the paddles
        Paddle leftPaddle = new Paddle(jounce, Player.Left);
        gameObjectManager.register(new LegacyGameObjectAdapter(leftPaddle));
        Paddle rightPaddle = new Paddle(jounce, Player.Right);
        gameObjectManager.register(new LegacyGameObjectAdapter(rightPaddle));

        // the ball
        Ball ball = new Ball(jounce, leftPaddle, rightPaddle);//, jounce.getSoundManager(), leftPaddle, rightPaddle);
        gameObjectManager.register(new LegacyGameObjectAdapter(ball));

        // paddle controllers
        gameObjectManager.register(new LegacyGameObjectAdapter(new BallChasingPaddleController(leftPaddle, ball)));
        gameObjectManager.register(new LegacyGameObjectAdapter(new BalancedAiPaddleController(jounce, rightPaddle, ball)));

        // the score
//        entityManager.addEntity(new ScoreBehavior(jounce, ball));
        gameObjectManager.register(new LegacyGameObjectAdapter(new TitleText(jounce)));

        Button newGameButton = new TextButton(jounce.getMultiTouchManager(), "New Game", screenBounds.getCenterX() - (125 / 2), screenBounds.getBottom() - 160, 125, 50);
        newGameButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void buttonReleased() {
                newGamePressed = true;
            }
        });
        gameObjectManager.register(new LegacyGameObjectAdapter(newGameButton));
        newGamePressed = false;

        jounce.clearScores();
    }

    @Override
    public void shutdown() {
        gameObjectManager.unregisterAll();
    }

    @Override
    public String getNextScreen() {
        if (newGamePressed) {
            return "game";
        } else {
            return super.getNextScreen();
        }
    }
}
