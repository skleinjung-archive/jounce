package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.component.CenterStripeRenderer;
import com.thrashplay.jounce.component.RectangleRenderer;
import com.thrashplay.jounce.component.TouchPaddleController;
import com.thrashplay.jounce.component.ai.DelayedBallChasingPaddleController;
import com.thrashplay.jounce.entity.BallSpawner;
import com.thrashplay.jounce.entity.GameObjectFactory;
import com.thrashplay.jounce.entity.ScoreDisplay;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.engine.EntityManagerScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.api.physics.CollisionDetector;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MatchScreen extends EntityManagerScreen {
    private Jounce jounce;
    private boolean backButtonPressed = false;

    private BackButtonListener backButtonListener = new BackButtonListener() {
        @Override
        public boolean onBackButtonPressed() {
            backButtonPressed = true;
            return true;
        }
    };

    public MatchScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        GameObjectFactory gameObjectFactory = new GameObjectFactory(jounce, entityManager);

        // the screen and background
        entityManager.addEntity(new ClearScreen(0x333333));

        Rectangle gameBounds = jounce.getGameBoardDimensions();

        // game board and background
        GameObject background = new GameObject("background");
        background.addComponent(new Position(gameBounds.getLeft(), gameBounds.getTop(), gameBounds.getWidth(), gameBounds.getHeight()));
        background.addComponent(new RectangleRenderer(0xff000000, true));
        background.addComponent(new CenterStripeRenderer(0x99ffffff));
        entityManager.addEntity(background);

        // components for the simulated match
        entityManager.addEntity(gameObjectFactory.createTopWall());
        entityManager.addEntity(gameObjectFactory.createBottomWall());
        entityManager.addEntity(gameObjectFactory.createLeftPaddle(new TouchPaddleController(jounce.getTouchManager())));
        entityManager.addEntity(gameObjectFactory.createRightPaddle(new DelayedBallChasingPaddleController(jounce, entityManager)));

        entityManager.addEntity(new BallSpawner(entityManager, gameObjectFactory, 2000));
        entityManager.addEntity(new CollisionDetector(entityManager));

        // title and new game button
        entityManager.addEntity(new ScoreDisplay(jounce));

        jounce.getBackButtonManager().addBackButtonListener(backButtonListener);

        jounce.clearScores();
        backButtonPressed = false;
    }

    @Override
    public void shutdown() {
        entityManager.removeAll();
        jounce.getBackButtonManager().removeBackButtonListener(backButtonListener);
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

}