package com.thrashplay.luna.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.thrashplay.luna.android.engine.LunaEngineComponent;
import com.thrashplay.luna.android.engine.LunaGame;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class LunaSurfaceView extends SurfaceView implements SurfaceHolder.Callback, GraphicsManager, LunaEngineComponent {

    private static final String TAG = "Luna.LunaSurfaceView";

    private SurfaceHolder holder;
    private Bitmap frameBuffer;

    public LunaSurfaceView(LunaGame game) {
        super(game);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        frameBuffer =  Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * Returns null if the drawing subsystem is not ready for rendering.
     */
    @Override
    public Graphics beginFrame() {
        if (frameBuffer != null) {
            return new DefaultGraphics(frameBuffer);
        } else {
            return null;
        }
    }

    @Override
    public void endFrame() {
        if (holder.getSurface().isValid() && frameBuffer != null) {
            Rect dstRect = new Rect();

            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void pause() {
        // no op
    }

    @Override
    public void resume() {
        // no op
    }
}
