package com.thrashplay.luna.android.engine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import com.thrashplay.luna.android.engine.loop.AbstractMainLoop;
import com.thrashplay.luna.android.engine.loop.FixedFpsMainLoop;
import com.thrashplay.luna.android.graphics.LunaSurfaceView;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class LunaGame extends Activity {
    private PowerManager.WakeLock wakeLock;
    private LunaSurfaceView surfaceView;
    private AbstractMainLoop mainLoop;
    private ScreenManager screenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullscreenNoTitle();
        keepScreenAwake();

        surfaceView = new LunaSurfaceView(this);
        setContentView(surfaceView);

        screenManager = new ScreenManager();
        mainLoop = new FixedFpsMainLoop(screenManager, surfaceView);
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
//        screen.resume();

        surfaceView.resume();
        mainLoop.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();

        mainLoop.pause();
        surfaceView.pause();

//        screen.pause();
//
//        if (isFinishing())
//            screen.dispose();
    }

    /**
     * Sets the activity's window to be fullscreen without a title.
     */
    private void setFullscreenNoTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Requests that the power manager keep the screen awake.
     */
    private void keepScreenAwake() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "LunaGame");
    }

    public LunaSurfaceView getSurfaceView() {
        return surfaceView;
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
