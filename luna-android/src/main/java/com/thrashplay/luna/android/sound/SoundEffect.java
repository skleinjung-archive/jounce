package com.thrashplay.luna.android.sound;

import android.media.SoundPool;

/**
 * TODO: Add class documentation
 *
 * @author Sean Kleinjung
 */
public class SoundEffect {
    private int soundId;
    private SoundPool soundPool;

    public SoundEffect(int soundId, SoundPool soundPool) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}
