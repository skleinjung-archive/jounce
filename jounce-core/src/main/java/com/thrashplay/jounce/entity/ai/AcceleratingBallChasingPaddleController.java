package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.entity.Paddle;
import com.thrashplay.jounce.entity.VectorBasedBall;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class AcceleratingBallChasingPaddleController implements Updateable {
    private VectorBasedBall ball;
    private Paddle paddle;

    private int acceleration = 0;
    private int maxAcceleration = 5;

    public AcceleratingBallChasingPaddleController(VectorBasedBall ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    public void update(Graphics graphics) {
        if (ball.getY() < paddle.getBounds().getCenterY()) {
            acceleration--;
        } else if (ball.getY() > paddle.getBounds().getCenterY()) {
            acceleration++;
        }

        if (acceleration < -maxAcceleration) {
            acceleration = -maxAcceleration;
        } else if (acceleration > maxAcceleration) {
            acceleration = maxAcceleration;
        }

        paddle.setVelocity(paddle.getVelocity() + acceleration);
    }
}
