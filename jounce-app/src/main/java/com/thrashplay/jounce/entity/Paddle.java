package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.android.engine.Updateable;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Paddle implements Updateable, Renderable {

    public enum CollisionZone {
        ExtremeTop,
        Top,
        Middle,
        Bottom,
        ExtremeBottom,
        OutOfBounds
    }

    private Jounce jounce;

    private Player player;
    private int y = 50;
    private float acceleration;
    private float velocity;


    public Paddle(Jounce jounce, Player player) {
        this.jounce = jounce;
        this.player = player;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle bounds = getBounds();
        graphics.fillRect(bounds.getLeftEdge(), bounds.getTopEdge(), bounds.getWidth(), bounds.getHeight(), 0xffff0000);
    }

    @Override
    public void update(Graphics graphics) {
        setVelocity(getVelocity() + getAcceleration());
        y += velocity;
        
        Rectangle gameBoardBounds = jounce.getGameBoardDimensions();
        Rectangle bounds = getBounds();
        if (bounds.getTopEdge() < gameBoardBounds.getTopEdge() + 5) {
            y = gameBoardBounds.getTopEdge() + 5;
            // stop when reaching an edge
            acceleration = 0;
            velocity = 0;
        } else if (bounds.getBottomEdge() > gameBoardBounds.getBottomEdge() - 5) {
            y = gameBoardBounds.getBottomEdge() - 5 - bounds.getHeight();
            // stop when reaching an edge
            acceleration = 0;
            velocity = 0;
        }
    }

    public float getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(float acceleration) {
        if (acceleration < 0) {
            this.acceleration = Math.max(acceleration, -getMaxAcceleration());
        } else {
            this.acceleration = Math.min(acceleration, getMaxAcceleration());
        }
    }

    public void setVelocity(float velocity) {
        if (velocity < 0) {
            this.velocity = Math.max(velocity, -getMaxVelocity());
        } else {
            this.velocity = Math.min(velocity, getMaxVelocity());
        }
    }

    public float getVelocity() {
        return velocity;
    }

    public int getMaxVelocity() {
        return (int) (20 * jounce.getWidthScalar());
    }

    public int getMaxAcceleration() {
        return (int) (20 * jounce.getWidthScalar());
    }

    public Rectangle getBounds() {
        int left;
        switch (player) {
            case Left:
                left = jounce.getGameBoardDimensions().getLeftEdge() + 8;
                break;

            case Right:
                left = jounce.getGameBoardDimensions().getRightEdge() - 8 - 15;
                break;

            default:
                throw new RuntimeException("Unknown Side: " + player);
        }

        return new Rectangle(left, y, 15, (int) (150 * jounce.getHeightScalar()));
    }
}
