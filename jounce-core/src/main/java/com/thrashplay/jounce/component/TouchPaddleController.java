package com.thrashplay.jounce.component;

import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.component.Movement;
import com.thrashplay.luna.api.component.UpdateableComponent;
import com.thrashplay.luna.api.input.TouchManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TouchPaddleController implements UpdateableComponent {
    private TouchManager touchManager;
    private int oldTouchY;

    public TouchPaddleController(TouchManager touchManager) {
        this.touchManager = touchManager;
    }

    @Override
    public void update(GameObject gameObject) {
        Movement movement = gameObject.getComponent(Movement.class);

        int velocity = 0;
        if (touchManager.isDown()) {
            if (touchManager.isDragging()) {
                velocity = (touchManager.getY() - oldTouchY) * 3;
            }

            movement.setVelocityY(velocity);
            oldTouchY = touchManager.getY();
        } else {
            movement.setVelocityY(0);
        }
    }
}
