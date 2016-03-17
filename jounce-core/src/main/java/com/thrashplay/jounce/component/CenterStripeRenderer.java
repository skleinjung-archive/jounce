package com.thrashplay.jounce.component;

import com.thrashplay.luna.api.component.GameObject;
import com.thrashplay.luna.api.component.Position;
import com.thrashplay.luna.api.component.RenderableComponent;
import com.thrashplay.luna.api.graphics.Graphics;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class CenterStripeRenderer implements RenderableComponent {

    private int color;

    public CenterStripeRenderer(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void render(Graphics graphics, GameObject gameObject) {
        Position position = gameObject.getComponent(Position.class);
        graphics.drawLine(position.getX() + position.getWidth() / 2,
                position.getY() + 5,
                position.getX() + position.getWidth() / 2,
                position.getY() + position.getHeight() - 5,
                color);
    }
}
