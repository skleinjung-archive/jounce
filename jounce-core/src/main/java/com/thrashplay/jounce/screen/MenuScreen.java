package com.thrashplay.jounce.screen;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.component.RectangleRenderer;
import com.thrashplay.jounce.component.ai.DelayedBallChasingPaddleController;
import com.thrashplay.jounce.entity.BallSpawner;
import com.thrashplay.jounce.entity.GameObjectFactory;
import com.thrashplay.jounce.entity.TitleText;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.engine.EntityManagerScreen;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.physics.CollisionDetector;
import com.thrashplay.luna.api.ui.Button;
import com.thrashplay.luna.api.ui.ButtonAdapter;
import com.thrashplay.luna.renderable.ClearScreen;
import com.thrashplay.luna.ui.TextButton;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MenuScreen extends EntityManagerScreen {
    private Jounce jounce;
    private boolean newGamePressed;

    public MenuScreen(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    protected void doInitialize() {
        Rectangle screenBounds = new Rectangle(0, 0, 480, 320);

        GameObjectFactory gameObjectFactory = new GameObjectFactory(jounce, entityManager);

        // the screen and background
        entityManager.addEntity(new ClearScreen(0x333333));

        Rectangle gameBounds = jounce.getGameBoardDimensions();

        // game board and background
        GameObject background = new GameObject("background");
        background.addComponent(new Position(gameBounds.getLeft(), gameBounds.getTop(), gameBounds.getWidth(), gameBounds.getHeight()));
        background.addComponent(new RectangleRenderer(0xff000000, true));
        entityManager.addEntity(background);

        // components for the simulated match
        entityManager.addEntity(gameObjectFactory.createTopWall());
        entityManager.addEntity(gameObjectFactory.createBottomWall());
        entityManager.addEntity(gameObjectFactory.createLeftPaddle(new DelayedBallChasingPaddleController(jounce, entityManager)));
        entityManager.addEntity(gameObjectFactory.createRightPaddle(new DelayedBallChasingPaddleController(jounce, entityManager)));

        entityManager.addEntity(new BallSpawner(entityManager, gameObjectFactory, 2000));
        entityManager.addEntity(new CollisionDetector(entityManager));

        // title and new game button
        entityManager.addEntity(new TitleText(jounce));

        Button newGameButton = new TextButton(jounce.getMultiTouchManager(), "New Game", screenBounds.getCenterX() - (125 / 2), screenBounds.getBottom() - 160, 125, 50);
        newGameButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void buttonReleased() {
                newGamePressed = true;
            }
        });
        entityManager.addEntity(newGameButton);
        newGamePressed = false;

        jounce.clearScores();
    }

    @Override
    public void shutdown() {
        entityManager.removeAll();
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