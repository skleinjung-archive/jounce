package com.thrashplay.jounce.component;

import com.thrashplay.jounce.entity.CollisionCategory;
import com.thrashplay.luna.api.component.*;
import com.thrashplay.luna.api.geom.Rectangle;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class PaddleCollisionHandler implements CollisionHandler {
    @Override
    public void handleCollision(GameObject ourObject, GameObject otherObject, Rectangle ourBoundingBox, Rectangle otherBoundingBox) {
        Position paddlePosition = ourObject.getComponent(Position.class);
        Movement paddleMovement = ourObject.getComponent(Movement.class);

        Collider otherCollisionConfig = otherObject.getComponent(Collider.class);
        switch (otherCollisionConfig.getCategory()) {
            case CollisionCategory.TOP_WALL:
                paddlePosition.setY(otherBoundingBox.getY() + otherBoundingBox.getHeight());
                paddleMovement.setVelocityY(0);
                break;

            case CollisionCategory.BOTTOM_WALL:
                paddlePosition.setY(otherBoundingBox.getY() - paddlePosition.getHeight());
                paddleMovement.setVelocityY(0);
                break;
        }
    }
}
