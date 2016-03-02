package com.thrashplay.luna.android.sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class SoundManager {

    private AssetManager assetManager;
    private SoundPool soundPool;

    public SoundManager(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assetManager = activity.getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    public SoundEffect createSoundEffect(String fileName) {
        try {
            AssetFileDescriptor assetDescriptor = assetManager.openFd(fileName);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new SoundEffect(soundId, soundPool);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + fileName + "'");
        }
    }
}
