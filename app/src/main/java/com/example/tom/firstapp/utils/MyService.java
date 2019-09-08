package com.example.tom.firstapp.utils;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.tom.firstapp.R;


public class MyService extends Service {

    static MediaPlayer MP;

    public MyService(){
    }

    @Override
    public IBinder onBind(Intent intent){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        MP = MediaPlayer.create(this, R.raw.discovery3);
        MP.setAudioStreamType(AudioManager.STREAM_MUSIC);
        MP.setLooping(true);
    }

    public static void start(){
        MP.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        MP.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public static void pause(){
        MP.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MP.stop();
    }
}
