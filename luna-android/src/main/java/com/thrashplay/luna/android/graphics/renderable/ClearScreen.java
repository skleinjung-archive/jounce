package com.thrashplay.luna.android.graphics.renderable;

import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class ClearScreen implements Renderable {

    private int color;

    public ClearScreen() {
        this(0);
    }

    public ClearScreen(int color) {
        this.color = color;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.clearScreen(color);
    }
}
