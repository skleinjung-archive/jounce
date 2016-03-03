package com.thrashplay.jounce.entity;

import android.graphics.Paint;
import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

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
        graphics.drawString("JOUNCE", gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 65, 0xffffffff, 108, Paint.Align.CENTER);
        graphics.drawString("Touch the screen to play", gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 25, 0xffffffff, 24, Paint.Align.CENTER);
    }
}