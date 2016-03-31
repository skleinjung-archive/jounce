package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.component.RectangleRenderer;
import com.thrashplay.jounce.component.ai.DelayedBallChasingPaddleController;
import com.thrashplay.jounce.entity.BallSpawner;
import com.thrashplay.jounce.entity.GameObjectFactory;
import com.thrashplay.jounce.entity.TitleText;
import com.thrashplay.luna.api.collision.CrossCollisionDetector;
import com.thrashplay.luna.api.collision.NoOpBroadPhaseCollisionDetector;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.engine.DefaultScreen;
import com.thrashplay.luna.api.engine.GameObject;
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
public class MenuScreen extends DefaultScreen {
    private Jounce jounce;
    private boolean newGamePressed;

    public MenuScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        Rectangle screenBounds = new Rectangle(0, 0, 480, 320);

        GameObjectFactory gameObjectFactory = new GameObjectFactory(jounce, gameObjectManager);

        // the screen and background
        gameObjectManager.register(new LegacyGameObjectAdapter(new ClearScreen(0x333333)));

        Rectangle gameBounds = jounce.getGameBoardDimensions();

        // game board and background
        GameObject background = new GameObject("background");
        background.addComponent(new Position(gameBounds.getLeft(), gameBounds.getTop(), gameBounds.getWidth(), gameBounds.getHeight()));
        background.addComponent(new RectangleRenderer(0xff000000, true));
        gameObjectManager.register(background);

        // components for the simulated match
        gameObjectManager.register(gameObjectFactory.createTopWall());
        gameObjectManager.register(gameObjectFactory.createBottomWall());
        gameObjectManager.register(gameObjectFactory.createLeftPaddle(new DelayedBallChasingPaddleController(jounce, gameObjectManager)));
        gameObjectManager.register(gameObjectFactory.createRightPaddle(new DelayedBallChasingPaddleController(jounce, gameObjectManager)));

        gameObjectManager.register(new LegacyGameObjectAdapter(new BallSpawner(gameObjectManager, gameObjectFactory, 2000)));

        GameObject collisionDetection = new GameObject("collision detection");
        collisionDetection.addComponent(new NoOpBroadPhaseCollisionDetector(gameObjectManager, new CrossCollisionDetector()));
        gameObjectManager.register(collisionDetection);

        // title and new game button
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
            return "match";
        } else {
            return super.getNextScreen();
        }
    }
}