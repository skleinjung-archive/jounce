package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.api.geom.Rectangle;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class ScoreDisplay implements Renderable {

    private Jounce jounce;

    public ScoreDisplay(Jounce jounce, Ball ball) {
        this.jounce = jounce;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle bounds = jounce.getGameBoardDimensions();
        int centerX = (int) (bounds.getLeft() + (bounds.getWidth() / 2f));
        int top = bounds.getTop();

        graphics.drawString(String.valueOf(jounce.getLeftPlayerScore()), centerX - 100, top + 115, 0xffffffff, 72, Graphics.HorizontalAlignment.Right);
        graphics.drawString(String.valueOf(jounce.getRightPlayerScore()), centerX + 100, top + 115, 0xffffffff, 72, Graphics.HorizontalAlignment.Left);
    }
}
