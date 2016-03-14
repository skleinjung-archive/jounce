package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;
import com.thrashplay.luna.math.Random;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class ReactionTimeLimitedBallChasingBehavior implements RandomBehaviorPaddleController.Behavior {

    private int minimumTimeBetweenDirectionChanges;
    private int maximumTimeBetweenDirectionChanges;

    private Jounce jounce;
    private int currentDirection = 0;
    private long nextDirectionChangeTime = 0;

    public ReactionTimeLimitedBallChasingBehavior(Jounce jounce, int minimumTimeBetweenDirectionChanges, int maximumTimeBetweenDirectionChanges) {
        this.jounce = jounce;
        this.minimumTimeBetweenDirectionChanges = minimumTimeBetweenDirectionChanges;
        this.maximumTimeBetweenDirectionChanges = maximumTimeBetweenDirectionChanges;
    }

    @Override
    public void updatePaddle(Paddle paddle, Ball ball) {


        // calculate a target zone on the paddle
//        int zoneTop = paddleBounds.getTop() + Random.getInteger(paddleBounds.getHeight());
//        int zoneBottom = zoneTop + Random.getInteger(paddleBounds.getHeight() - (zoneTop - paddleBounds.getTop()));

        if (ballIsMovingTowardsPaddle(paddle, ball)) {
            moveTowards(ball.getY(), paddle);
        } else {
            Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();
            int yPosition = Random.getInteger(gameBoardDimensions.getHeight() / 2) + gameBoardDimensions.getTop() + (gameBoardDimensions.getHeight() / 4);
            moveTowards(yPosition, paddle);
        }
    }

    private void moveTowards(float y, Paddle paddle) {
        int maxVelocity = (int) ((paddle.getMaxVelocity() / 4f) * 2);

        Rectangle paddleBounds = paddle.getBounds();
        if (paddleBounds.getBottom() > y) {
            if (currentDirection != -1) {
                if (System.currentTimeMillis() > nextDirectionChangeTime) {
                    paddle.setVelocity(-maxVelocity);
                    nextDirectionChangeTime = System.currentTimeMillis() + Random.getInteger(minimumTimeBetweenDirectionChanges, maximumTimeBetweenDirectionChanges);
                    currentDirection = -1;
                } else {
                    paddle.setVelocity(0);
                }
            } else {
                paddle.setVelocity(-maxVelocity);
            }
        } else if (paddleBounds.getTop() < y) {
            if (currentDirection != 1) {
                if (System.currentTimeMillis() > nextDirectionChangeTime) {
                    paddle.setVelocity(maxVelocity);
                    nextDirectionChangeTime = System.currentTimeMillis() + Random.getInteger(minimumTimeBetweenDirectionChanges, maximumTimeBetweenDirectionChanges);
                    currentDirection = 1;
                } else {
                    paddle.setVelocity(0);
                }
            } else {
                paddle.setVelocity(maxVelocity);
            }
        }
    }

    private boolean ballIsMovingTowardsPaddle(Paddle paddle, Ball ball) {
        Rectangle paddleBounds = paddle.getBounds();
        if (paddleBounds.getCenterX() > ball.getX()) {
            return ball.getAngle() < 90 || ball.getAngle() > 270;
        } else {
            return ball.getAngle() > 90 && ball.getAngle() < 270;
        }
    }
}
