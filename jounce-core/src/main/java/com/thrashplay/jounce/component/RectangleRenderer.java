package com.thrashplay.jounce.component;

import com.thrashplay.luna.api.engine.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.RenderableComponent;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * <code>RenderableComponent</code> that is used to display a circle sprite.
 *
 * @author Sean Kleinjung
 */
public class RectangleRenderer implements RenderableComponent {

    private int color;
    private boolean fill = true;

    public RectangleRenderer() {
    }

    public RectangleRenderer(int color) {
        this.color = color;
    }

    public RectangleRenderer(int color, boolean fill) {
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
        if (fill) {
            graphics.fillRect(position.getX(), position.getY(), position.getWidth(), position.getHeight(), color);
        } else {
            graphics.drawRect(position.getX(), position.getY(), position.getWidth(), position.getHeight(), color);
        }
    }
}
