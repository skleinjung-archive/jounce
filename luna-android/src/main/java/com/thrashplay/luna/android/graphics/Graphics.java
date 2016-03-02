package com.thrashplay.luna.android.graphics;

import android.graphics.Paint;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public interface Graphics {
    void clearScreen(int color);

    void drawLine(int x, int y, int x2, int y2, int color);

    void drawRect(int x, int y, int width, int height, int color);

    void fillRect(int x, int y, int width, int height, int color);

    void drawCircle(int x, int y, int radius, int paint);

    void fillCircle(int x, int y, int radius, int paint);

    void drawARGB(int a, int r, int g, int b);

    void drawString(String text, int x, int y, int color);

    public void drawString(String text, int x, int y, int color, int size);

    public void drawString(String text, int x, int y, int color, int size, Paint.Align align);

    int getWidth();

    int getHeight();
}
