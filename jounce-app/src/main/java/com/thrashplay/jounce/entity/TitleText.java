package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.api.graphics.Alignment;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TitleText implements Renderable {
    private Jounce jounce;

    public TitleText(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle gameBoardBounds = jounce.getGameBoardDimensions();
        graphics.drawString("JOUNCE", gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 65, 0xffffffff, 108, Alignment.Center);
        graphics.drawString("Touch the screen to play", gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 25, 0xffffffff, 24, Alignment.Center);
    }
}
