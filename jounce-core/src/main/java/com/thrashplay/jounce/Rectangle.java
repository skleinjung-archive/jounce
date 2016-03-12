package com.thrashplay.jounce;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class Rectangle {
    private int leftEdge;
    private int rightEdge;
    private int topEdge;
    private int bottomEdge;

    public Rectangle(int leftEdge, int topEdge, int width, int height) {
        this.leftEdge = leftEdge;
        this.rightEdge = leftEdge + width - 1;
        this.topEdge = topEdge;
        this.bottomEdge = topEdge + height - 1;
    }

    public int getLeftEdge() {
        return leftEdge;
    }

    public int getRightEdge() {
        return rightEdge;
    }

    public int getTopEdge() {
        return topEdge;
    }

    public int getBottomEdge() {
        return bottomEdge;
    }

    public int getWidth() {
        return rightEdge - leftEdge;
    }

    public int getHeight() {
        return bottomEdge - topEdge;
    }

    public int getCenterY() {
        return topEdge + getHeight() / 2;
    }

    public int getCenterX() {
        return leftEdge + getWidth() / 2;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "leftEdge=" + leftEdge +
                ", rightEdge=" + rightEdge +
                ", topEdge=" + topEdge +
                ", bottomEdge=" + bottomEdge +
                '}';
    }

}
