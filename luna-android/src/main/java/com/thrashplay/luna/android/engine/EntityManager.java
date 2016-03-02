package com.thrashplay.luna.android.engine;

import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class EntityManager implements Manager {
    private List<Renderable> drawables = new LinkedList<Renderable>();
    private List<Updateable> updateables = new LinkedList<Updateable>();

    public void addEntity(Object entity) {
        if (entity instanceof Renderable) {
            if (!drawables.contains(entity)) {
                drawables.add((Renderable) entity);
            }
        }

        if (entity instanceof Updateable) {
            if (!updateables.contains(entity)) {
                updateables.add((Updateable) entity);
            }
        }
    }

    public void updateAll(final Graphics g) {
        for (Updateable updateable : updateables) {
            updateable.update(g);
        }
    }

    public void renderAll(final Graphics g) {
        for (Renderable drawable : drawables) {
            drawable.render(g);
        }
    }
}
