package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Rectangle;
import com.thrashplay.jounce.Jounce;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class GameBoard implements Renderable {

    private Jounce jounce;

    public GameBoard(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();

        // black background
        graphics.fillRect(gameBoardDimensions.getLeftEdge(), gameBoardDimensions.getTopEdge(), gameBoardDimensions.getWidth(), gameBoardDimensions.getHeight(), 0xff000000);

        // walls
        graphics.fillRect(gameBoardDimensions.getLeftEdge(), gameBoardDimensions.getTopEdge(), gameBoardDimensions.getWidth(), 5, 0xffffffff);          // top
        graphics.fillRect(gameBoardDimensions.getLeftEdge(), gameBoardDimensions.getBottomEdge() - 5, gameBoardDimensions.getWidth(), 5, 0xffffffff);   // bottom

        // center paint line
        int middleOfBoard = gameBoardDimensions.getLeftEdge() + gameBoardDimensions.getWidth() / 2;
        graphics.drawLine(middleOfBoard, 0, middleOfBoard, gameBoardDimensions.getHeight(), 0x99ffffff);
    }
}
