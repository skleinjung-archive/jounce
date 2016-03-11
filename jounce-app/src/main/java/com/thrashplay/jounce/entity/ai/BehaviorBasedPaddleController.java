package com.thrashplay.jounce.entity.ai;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.jounce.entity.Ball;
import com.thrashplay.jounce.entity.Paddle;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BehaviorBasedPaddleController implements Updateable, Renderable {
    public static interface Behavior {
        public void updatePaddle(Paddle paddle, Ball ball);
        public boolean isComplete();
    }

    private Behavior currentBehavior;
    private Jounce jounce;
    private Paddle paddle;
    private Ball ball;

    public BehaviorBasedPaddleController(Jounce jounce, Paddle paddle, Ball ball) {
        this.jounce = jounce;
        this.paddle = paddle;
        this.ball = ball;

        changeBehavior();
    }

    @Override
    public void update(Graphics graphics) {
        currentBehavior.updatePaddle(paddle, ball);
        if (currentBehavior.isComplete()) {
            changeBehavior();
        }
    }

    @Override
    public void render(Graphics graphics) {
        if (currentBehavior instanceof MoveToPositionBehavior) {
            MoveToPositionBehavior behavior = (MoveToPositionBehavior) currentBehavior;
            Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();
            graphics.drawLine(gameBoardDimensions.getLeftEdge(), behavior.targetY, gameBoardDimensions.getRightEdge(), behavior.targetY, 0xffff0000);
        }
    }

    private void changeBehavior() {
        currentBehavior = new MoveToPositionBehavior((int) ball.getY() + (int) ball.getRadius());
    }

    private class MoveToPositionBehavior implements Behavior {
        private boolean complete = false;
        private int targetY;

        public MoveToPositionBehavior(int targetY) {
            this.targetY = targetY;
        }

        @Override
        public void updatePaddle(Paddle paddle, Ball ball) {
            Rectangle paddleBounds = paddle.getBounds();

            System.out.println("paddleBounds.getCenterY(): " + paddleBounds.getCenterY() + ", target=" + targetY);
            if (paddleBounds.getTopEdge() <= targetY && paddleBounds.getBottomEdge() >= targetY) {
                paddle.setAcceleration(0);
                paddle.setVelocity(0);
                complete = true;
            } else if (paddleBounds.getTopEdge() > targetY) {
                paddle.setVelocity(-paddle.getMaxVelocity());
            } else if (paddleBounds.getBottomEdge() < targetY) {
                paddle.setVelocity(paddle.getMaxVelocity());
            }
        }

        @Override
        public boolean isComplete() {
            return complete;
        }
    }
}
