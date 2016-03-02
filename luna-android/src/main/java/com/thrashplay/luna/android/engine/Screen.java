package com.thrashplay.luna.android.engine;

import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Screen implements Updateable, Renderable {

    protected EntityManager entityManager = new EntityManager();

    @Override
    public void render(Graphics graphics) {
        entityManager.renderAll(graphics);
    }

    @Override
    public void update(Graphics graphics) {
        doScreenUpdate();
        entityManager.updateAll(graphics);
    }

    protected void doScreenUpdate() {

    }
}
