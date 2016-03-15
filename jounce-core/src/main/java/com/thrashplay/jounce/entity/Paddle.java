package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Paddle implements Updateable, Renderable {

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
        graphics.fillRect(bounds.getLeft(), bounds.getTop(), bounds.getWidth(), bounds.getHeight(), 0xffffffff);
    }

    @Override
    public void update(Graphics graphics) {
        setVelocity(getVelocity() + getAcceleration());
        y += velocity;
        
        Rectangle gameBoardBounds = jounce.getGameBoardDimensions();
        Rectangle bounds = getBounds();
        if (bounds.getTop() < gameBoardBounds.getTop() + 5) {
            y = gameBoardBounds.getTop() + 5;
            // stop when reaching an edge
            acceleration = 0;
            velocity = 0;
        } else if (bounds.getBottom() > gameBoardBounds.getBottom() - 5) {
            y = gameBoardBounds.getBottom() - 5 - bounds.getHeight();
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
                left = jounce.getGameBoardDimensions().getLeft() + 8;
                break;

            case Right:
                left = jounce.getGameBoardDimensions().getRight() - 8 - 15;
                break;

            default:
                throw new RuntimeException("Unknown Side: " + player);
        }

        return new Rectangle(left, y, 15, (int) (150 * jounce.getHeightScalar()));
    }
}
