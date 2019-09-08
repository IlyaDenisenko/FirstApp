package com.example.tom.firstapp.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.tom.firstapp.R;

public class Sound {

    private int soundCheck;
    static int mStreamID;
    private static SoundPool soundPool;

    public static void enabledSoundPool(){
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        }  else {
            // Для новых устройств
            createNewSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void createNewSoundPool() {
        AudioAttributes aa = new AudioAttributes.Builder().
                setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(aa).build();
    }

    @SuppressWarnings("deprecation")
    private static void createOldSoundPool() {
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    public static int loadSound(Context context, Integer filename){
        return soundPool.load(context, filename, 1);
    }

    public static int playSound(int sound){
        return mStreamID = soundPool.play(sound,1,1,0,0,1);
    }
}
