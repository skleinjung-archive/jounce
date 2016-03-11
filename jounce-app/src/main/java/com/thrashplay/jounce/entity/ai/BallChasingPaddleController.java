package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.Rectangle;
import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallChasingPaddleController implements Updateable {
    private Ball ball;
    private Paddle paddle;

    private int velocity = 0;

    public BallChasingPaddleController(Paddle paddle, Ball ball) {
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    public void update(Graphics graphics) {
        int maxVelocity = (int) ((paddle.getMaxVelocity() / 4f) * 2);

        Rectangle paddleBounds = paddle.getBounds();
        if (ball.getY() < (paddleBounds.getTopEdge() + paddleBounds.getHeight() / 4)) {
            velocity = -maxVelocity;
        } else if (ball.getY() > (paddleBounds.getBottomEdge() - paddleBounds.getHeight() / 4)) {
            velocity = maxVelocity;
        } else {
            velocity = 0;
        }

        if (velocity < -maxVelocity) {
            velocity = -maxVelocity;
        } else if (velocity > maxVelocity) {
            velocity = maxVelocity;
        }

        paddle.setVelocity(velocity);
    }
}
