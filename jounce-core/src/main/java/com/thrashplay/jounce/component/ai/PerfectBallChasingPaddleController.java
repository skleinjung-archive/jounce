package com.thrashplay.jounce.component.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.entity.GameObjectFactory;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.engine.EntityManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class PerfectBallChasingPaddleController extends MoveToDestinationPaddleController {

    private Jounce jounce;
    private EntityManager entityManager;

    public PerfectBallChasingPaddleController(Jounce jounce, EntityManager entityManager) {
        super(jounce);
        this.jounce = jounce;
        this.entityManager = entityManager;
    }

    @Override
    protected int getNextDestination(GameObject gameObject) {
        Position position = gameObject.getComponent(Position.class);
        Position ballPosition = getBallPosition();

        // ball position can be null if we are waiting for a ball to spawn
        if (ballPosition != null) {
            int ballCenterY = ballPosition.getY() + (ballPosition.getHeight() / 2);
            return ballCenterY;
        } else {
            // no ball on gameboard currently, just stay where we are
            int paddleCenterY = position.getY() + (position.getHeight() / 2);
            return paddleCenterY;
        }
    }

    public Position getBallPosition() {
        for (Object entity : entityManager.getEntities()) {
            if (entity instanceof GameObject) {
                GameObject gameObject = (GameObject) entity;
                if (GameObjectFactory.ID_BALL.equals(gameObject.getId())) {
                    return gameObject.getComponent(Position.class);
                }
            }
        }

        // no ball was found
        return null;
    }
}
