package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.android.engine.Updateable;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;
import com.thrashplay.luna.android.sound.SoundEffect;
import com.thrashplay.luna.math.Angles;
import com.thrashplay.luna.math.Random;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Ball implements Renderable, Updateable {

    private Jounce jounce;

    // size and position of the ball
    private float x;
    private float y;
    private float radius;

    // the ball's movement parameters
    private float speed;
    private float angle;

    // the last positions of the ball, used for collision detection
    private float oldX;
    private float oldY;

    private Paddle leftPaddle;
    private Paddle rightPaddle;

    private SoundEffect paddleHitSound;
    private SoundEffect wallHitSound;

    public Ball(Jounce jounce, Paddle leftPaddle, Paddle rightPaddle) {
        this.jounce = jounce;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        paddleHitSound = jounce.getSoundManager().createSoundEffect("sfx/paddle_hit.wav");
        wallHitSound = jounce.getSoundManager().createSoundEffect("sfx/wall_hit.wav");
        radius = 10 * jounce.getWidthScalar();
        reset();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void reset() {
        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();

        if (jounce.getLastPlayerToScore() == Player.Left) {
            // serve from the right
            x = Random.getInteger(gameBoardDimensions.getCenterX() + 50 + (int) radius, gameBoardDimensions.getRightEdge() - 50);
            angle = Random.getInteger(2) == 0 ? 135 : 225;
        } else {
            // serve from the left
            x = Random.getInteger(gameBoardDimensions.getLeftEdge() + 50 + (int) radius, gameBoardDimensions.getCenterX() - 50);
            angle = Random.getInteger(2) == 0 ? 45 : 315;
        }

        y = Random.getInteger(gameBoardDimensions.getTopEdge() + 15, gameBoardDimensions.getBottomEdge() - 15);
        speed = getIniitalBallSpeed();
    }

    private float getIniitalBallSpeed() {
        return 14 * jounce.getWidthScalar();
    }

    @Override
    public void update(Graphics graphics) {
        double angleInRadians = Angles.toRadians(angle);
        float velocityX = (float) (speed * Math.cos(angleInRadians));
        float velocityY = -(float) (speed * Math.sin(angleInRadians)); // negate it because decreasing Y coordinates move up the screen

        oldX = x;
        oldY = y;

        x += velocityX;
        y += velocityY;

        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();
        handleWallCollision(gameBoardDimensions.getTopEdge() + 5);
        handleWallCollision(gameBoardDimensions.getBottomEdge() - 5);
        handlePaddleCollision(leftPaddle);
        handlePaddleCollision(rightPaddle);
    }

    /**
     * Check if the ball collided with a line positioned at the specified Y coordinate, and handle the collision if so.
     * @param collisionY
     */
    private void handleWallCollision(int collisionY) {
        if (angle > 0 && angle < 180) {
            // we are moving up
            boolean nowAboveTheLine = y - radius < collisionY;
            boolean previouslyAboveTheLine = oldY - radius < collisionY;
            if (nowAboveTheLine && !previouslyAboveTheLine) {
                y = collisionY + radius;
                invertDirectionY();
                wallHitSound.play(1.0f);
            }
        } else if (angle > 180 && angle < 360) {
            // we are moving down
            boolean nowBelowTheLine = y + radius > collisionY;
            boolean previouslyBelowTheLine = oldY + radius > collisionY;
            if (nowBelowTheLine &&  !previouslyBelowTheLine) {
                y = collisionY - radius;
                invertDirectionY();
                wallHitSound.play(1.0f);
            }
        }
    }

    private void handlePaddleCollision(Paddle paddle) {
        Rectangle paddleBounds = paddle.getBounds();
        boolean nowAbovePaddle = y + radius < paddleBounds.getTopEdge();
        boolean previouslyAbovePaddle = oldY + radius < paddleBounds.getTopEdge();
        boolean nowBelowPaddle = y - radius > paddleBounds.getBottomEdge();
        boolean previouslyBelowPaddle = oldY - radius > paddleBounds.getBottomEdge();
        if ((nowAbovePaddle && previouslyAbovePaddle) || (nowBelowPaddle && previouslyBelowPaddle)) {
            return;
        }

        if (angle > 90 && angle < 270) {
            // we are moving left
            int collisionX = paddleBounds.getRightEdge();

            boolean nowLeftOfTheLine = x - radius < collisionX;
            boolean previouslyLeftOfTheLine = oldX - radius < collisionX;
            if (nowLeftOfTheLine && !previouslyLeftOfTheLine) {
                x = collisionX + radius;
                updateMovementAfterPaddleCollision(paddle, true);
                paddleHitSound.play(1.0f);
            }
        } else if (angle < 90 || angle > 270) {
            // we are moving right
            int collisionX = paddleBounds.getLeftEdge();

            boolean nowRightOfTheLine = x + radius > collisionX;
            boolean previouslyRightOfTheLine = oldX + radius > collisionX;
            if (nowRightOfTheLine && !previouslyRightOfTheLine) {
                x = collisionX - radius;
                updateMovementAfterPaddleCollision(paddle, false);
                paddleHitSound.play(1.0f);
            }
        }
    }

    /**
     * Inverts the ball's vertical direction, used when bouncing off of the top and bottom walls.
     */
    private void invertDirectionY() {
        angle = 360 - angle;
    }

    /**
     * Update the ball's speed and angle after colliding with the specified paddle.
     */
    private void updateMovementAfterPaddleCollision(Paddle paddle, boolean movingRight) {
        Rectangle bounds = paddle.getBounds();
        float zonePercent = (y - bounds.getTopEdge()) / bounds.getHeight();
        if (movingRight) {
            angle = Angles.normalize(60 - (120 * zonePercent));
        } else {
            angle = 120 + (120 * zonePercent);
        }

        if (zonePercent < 15 || zonePercent > 85) {
//            speed *= 1.1;
            speed += 1.25 * jounce.getWidthScalar();
        } else {
//            speed *= 1.025;
            speed += 0.5 * jounce.getWidthScalar();
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.fillCircle((int) x, (int) y, (int) radius, 0xffffffff);
    }
}
