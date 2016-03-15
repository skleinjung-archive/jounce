package com.thrashplay.jounce.component.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Movement;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.UpdateableComponent;
import com.thrashplay.luna.api.geom.Rectangle;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public abstract class MoveToDestinationPaddleController implements UpdateableComponent {

    private Jounce jounce;

    private int currentDestination;
    private boolean destinationSet = false;

    public MoveToDestinationPaddleController(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void update(GameObject gameObject) {
        Position position = gameObject.getComponent(Position.class);
        Movement movement = gameObject.getComponent(Movement.class);

        int paddleCenterY = position.getY() + (position.getHeight() / 2);

        if (paddleCenterY == currentDestination || !destinationSet) {
            currentDestination = checkBounds(position, getNextDestination(gameObject));
            destinationSet = true;
        }

        movement.setVelocityY(currentDestination - paddleCenterY);
    }

    private int checkBounds(Position paddlePosition, int ballCenterY) {
        Rectangle gameBounds = jounce.getGameBoardDimensions();
        int topBound = gameBounds.getTop() + paddlePosition.getHeight() / 2 + 10;
        int bottomBound = gameBounds.getBottom() - paddlePosition.getHeight() / 2 - 10;
        if (ballCenterY < topBound) {
            return topBound;
        } else if (ballCenterY > bottomBound) {
            return bottomBound;
        } else {
            return ballCenterY;
        }
    }

    protected abstract int getNextDestination(GameObject gameObject);
}
