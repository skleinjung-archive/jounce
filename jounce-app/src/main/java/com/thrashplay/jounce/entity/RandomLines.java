package com.thrashplay.jounce.entity;

import com.thrashplay.luna.api.engine.Updateable;
import com.thrashplay.luna.api.graphics.Graphics;
import com.thrashplay.luna.api.graphics.Renderable;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class RandomLines implements Renderable, Updateable {

    private int msPerLine = 32;


    private List<Line> lines = new LinkedList<Line>();

    @Override
    public void update(Graphics graphics) {
        if (lines.size() > 15) {
            lines.clear();
        }

        Line line = new Line();
        line.x1 = getRandom(graphics.getWidth() - 4) + 2;
        line.y1 = getRandom(graphics.getHeight() - 4) + 2;
        line.x2 = getRandom(graphics.getWidth() - 4) + 2;
        line.y2 = getRandom(graphics.getHeight() - 4) + 2;
        line.color = getRandomColor();
        lines.add(line);
    }

    @Override
    public void render(Graphics graphics) {
        for (Line line : lines) {
            graphics.drawLine(line.x1, line.y1, line.x2, line.y2, line.color);
        }
    }

    private int getRandomColor() {
        int a = 255;
        int r = getRandom(255);
        int g = getRandom(255);
        int b = getRandom(255);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private int getRandom(int max) {
        return (int) (Math.random() * max);
    }

    private class Line {
        int x1, x2, y1, y2;
        int color;
    }
}
