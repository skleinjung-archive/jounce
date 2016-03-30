package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.component.CenterStripeRenderer;
import com.thrashplay.jounce.component.RectangleRenderer;
import com.thrashplay.jounce.component.TouchPaddleController;
import com.thrashplay.jounce.component.ai.DelayedBallChasingPaddleController;
import com.thrashplay.jounce.entity.BallSpawner;
import com.thrashplay.jounce.entity.GameObjectFactory;
import com.thrashplay.jounce.entity.ScoreDisplay;
import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.input.BackButtonListener;
import com.thrashplay.luna.api.collision.BoundingBoxCollisionDetector;
import com.thrashplay.luna.engine.LegacyGameObjectAdapter;
import com.thrashplay.luna.renderable.ClearScreen;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MatchScreen extends DefaultScreen {
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
        GameObjectFactory gameObjectFactory = new GameObjectFactory(jounce, gameObjectManager);

        // the screen and background
        gameObjectManager.register(new LegacyGameObjectAdapter(new ClearScreen(0x333333)));

        Rectangle gameBounds = jounce.getGameBoardDimensions();

        // game board and background
        GameObject background = new GameObject("background");
        background.addComponent(new Position(gameBounds.getLeft(), gameBounds.getTop(), gameBounds.getWidth(), gameBounds.getHeight()));
        background.addComponent(new RectangleRenderer(0xff000000, true));
        background.addComponent(new CenterStripeRenderer(0x99ffffff));
        gameObjectManager.register(background);

        // components for the simulated match
        gameObjectManager.register(gameObjectFactory.createTopWall());
        gameObjectManager.register(gameObjectFactory.createBottomWall());
        gameObjectManager.register(gameObjectFactory.createLeftPaddle(new TouchPaddleController(jounce.getTouchManager())));
        gameObjectManager.register(gameObjectFactory.createRightPaddle(new DelayedBallChasingPaddleController(jounce, gameObjectManager)));

        gameObjectManager.register(new LegacyGameObjectAdapter(new BallSpawner(gameObjectManager, gameObjectFactory, 2000)));
        gameObjectManager.register(new LegacyGameObjectAdapter(new BoundingBoxCollisionDetector(gameObjectManager)));

        // title and new game button
        gameObjectManager.register(new LegacyGameObjectAdapter(new ScoreDisplay(jounce)));

        jounce.getBackButtonManager().addBackButtonListener(backButtonListener);

        jounce.clearScores();
        backButtonPressed = false;
    }

    @Override
    public void shutdown() {
        gameObjectManager.unregisterAll();
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