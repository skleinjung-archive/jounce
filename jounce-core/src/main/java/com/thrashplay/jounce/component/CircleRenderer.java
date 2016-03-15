package com.thrashplay.jounce.component;

import com.thrashplay.jounce.JounceException;
import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.RenderableComponent;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * <code>RenderableComponent</code> that is used to display a circle sprite.
 *
 * @author Sean Kleinjung
 */
public class CircleRenderer implements RenderableComponent {

    private int color;
    private boolean fill = true;

    public CircleRenderer() {
    }

    public CircleRenderer(int color) {
        this.color = color;
    }

    public CircleRenderer(int color, boolean fill) {
        this.color = color;
        this.fill = fill;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    @Override
    public void render(Graphics graphics, GameObject gameObject) {
        Position position = gameObject.getComponent(Position.class);
        if (position.getWidth() != position.getHeight()) {
            throw new JounceException("Game object must have identical height and width to use circle renderer.");
        }

        int radius = position.getWidth() / 2;
        int centerX = position.getX() + radius;
        int centerY = position.getY() + radius;

        if (fill) {
            graphics.fillCircle(centerX, centerY, radius, color);
        } else {
            graphics.drawCircle(centerX, centerY, radius, color);
        }
    }
}
