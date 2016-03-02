package com.thrashplay.luna.android.input;

import android.view.MotionEvent;
import android.view.View;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class TouchManager implements View.OnTouchListener {

    private boolean down;
    private boolean dragging;
    private int x;
    private int y;

    public TouchManager(View view) {
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized(this) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    down = true;
                    dragging = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    down = true;
                    dragging = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    down = false;
                    dragging = false;
                    break;
            }

            x = (int) event.getX();
            y = (int) event.getY();

            return true;
        }
    }

    public boolean isDown() {
        return down;
    }

    public boolean isDragging() {
        return dragging;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
