package com.example.tom.firstapp;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;


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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        MP.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        MP.stop();
    }
}
