package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.api.sound.SoundEffect;
import com.thrashplay.luna.api.sound.SoundManager;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class VectorBasedBall implements Renderable, Updateable {

    private static final int DEFAULT_RADIUS = 10;
    private static final float SECONDS_TO_CROSS_BOARD = 2.6f;
    private static final int UPDATES_PER_SECONDS = 60;

    private Jounce jounce;

    private Paddle leftPaddle;
    private Paddle rightPaddle;

    private float x = 50;
    private float y = 50;
    private int radius = DEFAULT_RADIUS;

    private float velocityX;
    private float velocityY;

    private boolean englished;

    private SoundEffect wallBounceSound;
    private SoundEffect paddleHitSound;

    public VectorBasedBall(Jounce jounce, SoundManager soundManager, Paddle leftPaddle, Paddle rightPaddle) {
        this.jounce = jounce;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.wallBounceSound = soundManager.createSoundEffect("sfx/wall_hit.mp3");
        this.paddleHitSound = soundManager.createSoundEffect("sfx/paddle_hit.mp3");
        reset();
    }

    public void reset() {
        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();
        y = gameBoardDimensions.getTopEdge() + (int) (Math.random() * (gameBoardDimensions.getHeight() - 20)) + 10;

        float desiredSpeedInPixelsPerSecond = (float) gameBoardDimensions.getWidth() / SECONDS_TO_CROSS_BOARD;
        float desiredSpeedInPixelsPerUpdate = desiredSpeedInPixelsPerSecond / 60;
        velocityY = (int) desiredSpeedInPixelsPerUpdate;

        if (jounce.getLastPlayerToScore() == Player.Left) {
            x = gameBoardDimensions.getCenterX() + (int) (Math.random() * (gameBoardDimensions.getWidth() / 2)) - 10;
            velocityX = -(int) desiredSpeedInPixelsPerUpdate;
        } else if (jounce.getLastPlayerToScore() == null || jounce.getLastPlayerToScore() == Player.Right) {
            x = gameBoardDimensions.getLeftEdge() + (int) (Math.random() * (gameBoardDimensions.getWidth() / 2)) + 10;
            velocityX = (int) desiredSpeedInPixelsPerUpdate;
        }
    }

    public int getX() {
        return (int) x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return (int) y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    @Override
    public void update(Graphics graphics) {
        x += velocityX;
        y += velocityY;

        // bounce off top
        if ((y - radius) < jounce.getGameBoardDimensions().getTopEdge()) {
            velocityY = -velocityY;
            wallBounceSound.play(0.5f);
        }
        // bounce off bottom
        if ((y + radius) > jounce.getGameBoardDimensions().getBottomEdge()) {
            velocityY = -velocityY;
            wallBounceSound.play(0.5f);
        }

        // bounce off left paddle
        Rectangle bounds = leftPaddle.getBounds();
        if ((x - radius) < bounds.getRightEdge()) {
            int paddleTop = bounds.getTopEdge();
            int paddleBottom = bounds.getBottomEdge();

            if (y >= paddleTop && y <= paddleBottom) {
                // positive velocity after bouncing off of left paddle
                velocityX = Math.abs(velocityX);

                int english = getEnglish(leftPaddle.getVelocity(), leftPaddle.getMaxVelocity());
                englished = english != 0;
                velocityY += english;

                paddleHitSound.play(0.75f);
            }
        }


        // bounce off right paddle
        bounds = rightPaddle.getBounds();
        if ((x + radius) > bounds.getLeftEdge()) {
            int paddleTop = bounds.getTopEdge();
            int paddleBottom = bounds.getBottomEdge();

            if (y >= paddleTop && y <= paddleBottom) {
                // negative velocity after bouncing off of right paddle
                velocityX = -Math.abs(velocityX);

                int english = getEnglish(rightPaddle.getVelocity(), rightPaddle.getMaxVelocity());
                englished = english != 0;
                velocityY += english;

                paddleHitSound.play(0.75f);
            }
        }
    }

    private int getEnglish(float velocity, int maxVelocity) {
        int sign = (velocity < 0) ? -1 : 1;
        velocity = Math.abs(velocity);
        System.out.println("velocity=" + velocity + ", sign=" + sign + ", maxVel=" + maxVelocity);
        if (velocity >= (0.9f * maxVelocity)) {
            return sign * 4;
        } else if (velocity >= (0.75f * maxVelocity)) {
            return sign * 2;
        } else if (velocity >= (0.5f * maxVelocity)) {
            return sign * 2;
        } else if (velocity >= (0.25f * maxVelocity)) {
            return sign;
        }
        return 0;
    }

    @Override
    public void render(Graphics graphics) {
        int color = englished ? 0xffff0000 : 0xffffffff;
        graphics.fillCircle((int) x, (int) y, radius, color);
    }
}
