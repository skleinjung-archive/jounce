package com.thrashplay.jounce.component;

import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.UpdateableComponent;
import com.thrashplay.luna.api.engine.EntityManager;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallFadeOutAnimator implements UpdateableComponent {
    private EntityManager entityManager;

    public BallFadeOutAnimator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void update(GameObject gameObject) {
        Position position = gameObject.getComponent(Position.class);
        position.setWidth((int) (position.getWidth() * 0.83));
        position.setHeight((int) (position.getHeight() * 0.83));

        if (position.getWidth() < 3) {
            entityManager.removeEntity(gameObject);
        }
    }
}
