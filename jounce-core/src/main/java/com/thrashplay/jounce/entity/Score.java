package com.thrashplay.jounce.entity;

import com.thrashplay.jounce.Jounce;
import com.thrashplay.jounce.Rectangle;
import com.thrashplay.luna.api.sound.SoundEffect;
import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Score implements Renderable, Updateable {

    private Jounce jounce;
    private Ball ball;
    private SoundEffect outOfBoundsSound;

    private long scoreTimestamp = 0;
    private float originalRadius;
    private long freezeDelay = 75;
    private long resetDelay = 400;

    public Score(Jounce jounce, Ball ball) {
        this.jounce = jounce;
        this.ball = ball;

        outOfBoundsSound = jounce.getSoundManager().createSoundEffect("sfx/out_of_bounds.mp3");
    }

    @Override
    public void update(Graphics graphics) {
        if (scoreTimestamp != 0) {
            if (System.currentTimeMillis() >= scoreTimestamp + resetDelay) {
                scoreTimestamp = 0;
                ball.reset();
                ball.setRadius(originalRadius);
            } else if (System.currentTimeMillis() >= scoreTimestamp + freezeDelay) {
                ball.setSpeed(28);
                ball.setRadius(ball.getRadius() * 0.83f);
            } else {
                ball.setSpeed(0);
            }
        } else {
            Rectangle gameBoardDimensions = jounce.getGameBoardDimensions();

            if (ball.getX() < gameBoardDimensions.getLeftEdge()) {
                jounce.addPointForRightPlayer();
                scoreTimestamp = System.currentTimeMillis();
                originalRadius = ball.getRadius();

                outOfBoundsSound.play(1.0f);
            }

            if (ball.getX() > gameBoardDimensions.getRightEdge()) {
                jounce.addPointForLeftPlayer();
                scoreTimestamp = System.currentTimeMillis();
                originalRadius = ball.getRadius();

                outOfBoundsSound.play(1.0f);
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        Rectangle bounds = jounce.getGameBoardDimensions();
        int centerX = (int) (bounds.getLeftEdge() + (bounds.getWidth() / 2f));
        int top = bounds.getTopEdge();

        graphics.drawString(String.valueOf(jounce.getLeftPlayerScore()), centerX - 100, top + 115, 0xffffffff, 72, Graphics.HorizontalAlignment.Right);
        graphics.drawString(String.valueOf(jounce.getRightPlayerScore()), centerX + 100, top + 115, 0xffffffff, 72, Graphics.HorizontalAlignment.Left);
    }
}
