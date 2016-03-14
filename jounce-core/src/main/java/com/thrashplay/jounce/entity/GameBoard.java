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
public class GameBoard implements Renderable {

    private Jounce jounce;

    public GameBoard(Jounce jounce) {
        this.jounce = jounce;
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();

        // black background
        graphics.fillRect(gameBoardDimensions.getLeft(), gameBoardDimensions.getTop(), gameBoardDimensions.getWidth(), gameBoardDimensions.getHeight(), 0xff000000);

        // walls
        graphics.fillRect(gameBoardDimensions.getLeft(), gameBoardDimensions.getTop(), gameBoardDimensions.getWidth(), 5, 0xffffffff);          // top
        graphics.fillRect(gameBoardDimensions.getLeft(), gameBoardDimensions.getBottom() - 5, gameBoardDimensions.getWidth(), 5, 0xffffffff);   // bottom

        // center paint line
        int middleOfBoard = gameBoardDimensions.getLeft() + gameBoardDimensions.getWidth() / 2;
        graphics.drawLine(middleOfBoard, 0, middleOfBoard, gameBoardDimensions.getHeight(), 0x99ffffff);
    }
}
