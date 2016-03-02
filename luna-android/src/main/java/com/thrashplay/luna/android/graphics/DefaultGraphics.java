package com.thrashplay.luna.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * TODO: Add class documentation
 */
public class DefaultGraphics implements Graphics {
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint;

    public DefaultGraphics(Bitmap frameBuffer) {
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public void fillRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public void drawCircle(int x, int y, int radius, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void fillCircle(int x, int y, int radius, int color) {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void drawARGB(int a, int r, int g, int b) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawARGB(a, r, g, b);
    }

    @Override
    public void drawString(String text, int x, int y, int color) {
        drawString(text, x, y, color, 12);
    }

    @Override
    public void drawString(String text, int x, int y, int color, int size) {
        drawString(text, x, y, color, size, Paint.Align.LEFT);
    }

    @Override
    public void drawString(String text, int x, int y, int color, int size, Paint.Align align) {
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
