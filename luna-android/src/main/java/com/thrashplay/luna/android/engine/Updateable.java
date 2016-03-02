package com.thrashplay.luna.android.engine;

import com.thrashplay.luna.android.graphics.Graphics;

/**
 * Interface for game entities that can be updated.
 *
 * @author Sean Kleinjung
 */
public interface Updateable {
    void update(Graphics graphics);
}
