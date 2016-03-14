package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.input.TouchManager;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TouchPaddleController implements Updateable {

    private TouchManager touchManager;

    private Paddle paddle;

    private int oldTouchY;
    private boolean isDragging;

    public TouchPaddleController(Jounce jounce, Paddle paddle) {
        this.touchManager = jounce.getTouchManager();
        this.paddle = paddle;
    }

    @Override
    public void update(Graphics graphics) {
        int velocity = 0;
        if (touchManager.isDown()) {
            if (isDragging) {
                velocity = (touchManager.getY() - oldTouchY) * 3;
            }

            paddle.setVelocity(velocity);

            oldTouchY = touchManager.getY();
            isDragging = true;
        } else {
            isDragging = false;
            paddle.setVelocity(0);
        }
    }
}