package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class DebugString implements Renderable {

    private Jounce jounce;
    private VectorBasedBall ball;
    private Paddle paddle;

    public DebugString(Jounce jounce, VectorBasedBall ball, Paddle paddle) {
        this.jounce = jounce;
        this.ball = ball;
        this.paddle = paddle;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle bounds = jounce.getGameBoardDimensions();

        String string = String.format("Ball {xVel=%d, yVel=%d}; Paddle {velocity=%d}", ball.getVelocityX(), ball.getVelocityY(), paddle.getVelocity());
        graphics.drawString(string, bounds.getLeft() + 5, bounds.getBottom() - 15, 0xffff0000, 18);
    }
}
