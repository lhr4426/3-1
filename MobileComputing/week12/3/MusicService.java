package com.example.mc_week12_prac3;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mp;
    IBinder mBinder = new MusicServiceBinder();

    class MusicServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("hwang", "onBind()");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.i("hwang", "onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i("hwang", "onDestroy()");
        if (mp != null) mp.stop();
        super.onDestroy();
    }
    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        Log.i("hwang", "onStartCommand()");
        mp = MediaPlayer.create(this, intent.getIntExtra("song",0));
        mp.setLooping(true);
        mp.start();

        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    // for bind method
    public void play(int song){
        mp = MediaPlayer.create(this,song);
        mp.setLooping(true);
        mp.start();
    }

    public MediaPlayer getMP(){
        return mp;

    }
}
