package com.thrashplay.jounce.component;

import com.thrashplay.jounce.entity.CollisionCategory;
import com.thrashplay.luna.api.component.*;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.math.Angles;
import com.thrashplay.luna.api.sound.SoundEffect;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallCollisionHandler implements CollisionHandler {

    private SoundEffect paddleHitSoundEffect;
    private SoundEffect wallHitSoundEffect;

    public BallCollisionHandler(SoundEffect paddleHitSoundEffect, SoundEffect wallHitSoundEffect) {
        this.paddleHitSoundEffect = paddleHitSoundEffect;
        this.wallHitSoundEffect = wallHitSoundEffect;
    }

    @Override
    public void handleCollision(GameObject ourObject, GameObject otherObject, Rectangle ourBoundingBox, Rectangle otherBoundingBox) {
        Position ballPosition = ourObject.getComponent(Position.class);
        Movement ballMovement = ourObject.getComponent(Movement.class);
        Collider otherCollisionConfig = otherObject.getComponent(Collider.class);

        switch (otherCollisionConfig.getCategory()) {
            case CollisionCategory.TOP_WALL:
                ballPosition.setY(otherBoundingBox.getBottom());
                ballMovement.setVelocityY(-ballMovement.getVelocityY());
                wallHitSoundEffect.play(1.0f);
                break;

            case CollisionCategory.BOTTOM_WALL:
                ballPosition.setY(otherBoundingBox.getTop() - ballPosition.getHeight());
                ballMovement.setVelocityY(-ballMovement.getVelocityY());
                wallHitSoundEffect.play(1.0f);
                break;

            case CollisionCategory.LEFT_PADDLE:
                ballPosition.setX(otherBoundingBox.getRight());
                setVelocities(ballMovement, ourBoundingBox, otherBoundingBox, true);
                paddleHitSoundEffect.play(1.0f);
                break;

            case CollisionCategory.RIGHT_PADDLE:
                ballPosition.setX(otherBoundingBox.getLeft() - ballPosition.getWidth());
                setVelocities(ballMovement, ourBoundingBox, otherBoundingBox, false);
                paddleHitSoundEffect.play(1.0f);
                break;
        }
    }

    private void setVelocities(Movement ballMovement, Rectangle ourBoundingBox, Rectangle otherBoundingBox, boolean movingRight) {
        float speed = ballMovement.getCurrentSpeed();

        // calculate a new angle based on where on the paddle the ball struck
        int ballCenterY = ourBoundingBox.getY() + (ourBoundingBox.getHeight() / 2);
        float zonePercent = (ballCenterY - otherBoundingBox.getTop()) / (float) otherBoundingBox.getHeight();
        float angle;
        if (movingRight) {
            angle = Angles.normalize(60 - (120 * zonePercent));
        } else {
            angle = 120 + (120 * zonePercent);
        }

        if (zonePercent < 15 || zonePercent > 85) {
//            speed *= 1.1;
            speed *= 1.05;
//            speed += 1.25 * jounce.getWidthScalar();
        } else {
//            speed *= 1.025;
            speed *= 1.025;
//            speed += 0.5 * jounce.getWidthScalar();
        }

        ballMovement.setAngleAndSpeed(angle, speed);
    }
}
