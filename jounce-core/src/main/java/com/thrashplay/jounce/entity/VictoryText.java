package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class VictoryText implements Renderable {
    private Jounce jounce;

    public VictoryText(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle gameBoardBounds = jounce.getGameBoardDimensions();
        String message;
        if (jounce.getLeftPlayerScore() > jounce.getRightPlayerScore()) {
            message = "Player Wins";
        } else {
            message = "AI Wins";
        }
        graphics.drawString(message, gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 65, 0xffffffff, 108, Graphics.HorizontalAlignment.Center);
        graphics.drawString("Use the back button to play again", gameBoardBounds.getCenterX(), gameBoardBounds.getBottomEdge() - 25, 0xffffffff, 24, Graphics.HorizontalAlignment.Center);
    }
}
