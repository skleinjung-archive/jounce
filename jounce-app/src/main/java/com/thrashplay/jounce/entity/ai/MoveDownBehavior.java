package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class MoveDownBehavior implements RandomBehaviorPaddleController.Behavior {
    @Override
    public void updatePaddle(Paddle paddle, Ball ball) {
        paddle.setVelocity(paddle.getMaxVelocity());
    }
}
