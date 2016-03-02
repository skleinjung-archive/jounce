package com.thrashplay.jounce.entity;

import com.thrashplay.luna.android.engine.Updateable;
import com.thrashplay.luna.android.graphics.Graphics;
import com.thrashplay.luna.android.graphics.Renderable;
import com.thrashplay.luna.math.Vector2D;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class BallTrails implements Updateable, Renderable {

    private int maxRadius = 8;

    private VectorBasedBall ball;
    private List<Vector2D> trailLocations = new LinkedList<Vector2D>();

    public BallTrails(VectorBasedBall ball) {
        this.ball = ball;
    }

    @Override
    public void render(Graphics graphics) {
        int radius = maxRadius;
        for (int i = trailLocations.size() - 1; i >= 0; i--) {
            Vector2D location = trailLocations.get(i);
            graphics.fillCircle(location.getX(), location.getY(), radius, 0xff999999);
            radius = (int) (radius * 0.8);
        }

    }

    @Override
    public void update(Graphics graphics) {
        trailLocations.add(new Vector2D(ball.getX(), ball.getY()));
        if (trailLocations.size() > 4) {
            trailLocations.remove(0);
        }
    }

}
