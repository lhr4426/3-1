package com.example.mc_week12_prac3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    Button btnPlay, btnPause, btnStop;

    MediaPlayer mPlayer;
    boolean PAUSED = false; // 음악이 일시정지 중인지 체크.


    TextView tvMP3;
    ProgressBar pbMP3;

    String selectedMP3;

    Switch switch1;
    SeekBar seekMP3;
    TextView tvTime;


    MusicService ms;   // for bind service;
    boolean isService = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 MP3 플레이어");


        btnPlay = (Button) findViewById(R.id.play_btn);
        btnPause = (Button) findViewById(R.id.pause_btn);
        btnStop = (Button) findViewById(R.id.stop_btn);


        tvMP3 = (TextView) findViewById(R.id.tvMP3);
        pbMP3 = (ProgressBar) findViewById(R.id.pbMP3);

        selectedMP3 = "song1.mp3";

        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song1);
//                mPlayer.prepare();
                mPlayer.setLooping(true);
                mPlayer.start();


                btnPlay.setClickable(false);
                btnPause.setClickable(true);
                btnStop.setClickable(true);


                tvMP3.setText("실행중인 음악 :  " + selectedMP3);
                pbMP3.setVisibility(View.VISIBLE);

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (PAUSED == false) {
                    mPlayer.pause();
                    btnPause.setText("이어듣기");
                    PAUSED = true;

                    pbMP3.setVisibility(View.INVISIBLE);
                } else {
                    mPlayer.start();
                    PAUSED = false;
                    btnPause.setText("일시정지");

                    pbMP3.setVisibility(View.VISIBLE);
                }
            }
        });
        btnPause.setClickable(false);

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPlayer.stop();
                mPlayer.reset();

                btnPlay.setClickable(true);
                btnPause.setClickable(false);

                btnPause.setText("일시정지");
                btnStop.setClickable(false);

                tvMP3.setText("실행중인 음악 :  ");
                pbMP3.setVisibility(View.INVISIBLE);
            }
        });
        btnStop.setClickable(false);

        seekMP3 = (SeekBar) findViewById(R.id.seekMP3);
        switch1 = (Switch) findViewById(R.id.switch1);
        tvTime = (TextView) findViewById(R.id.textTime);

        switch1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (switch1.isChecked()) {
                    mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song1);
                    mPlayer.start();
                    makeThread();
                } else {
                    mPlayer.stop();
                }
            }
        });

        seekMP3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);
                }

            }
        });
    }

    void makeThread() {
        new Thread() {
            public void run() {
                // 음악이 계속 작동 중이라면
                final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                if (mPlayer == null) return;
                seekMP3.setMax(mPlayer.getDuration()); // 음악의 시간을 최대로 설정

                while (mPlayer.isPlaying()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekMP3.setProgress(mPlayer.getCurrentPosition());  // runOnUIThread 에서 안해도 no error.
                            tvTime.setText(String.format("진행 시간 : " + timeFormat.format(mPlayer.getCurrentPosition())));
                        }
                    });
                    SystemClock.sleep(100);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekMP3.setProgress(0);
                        switch1.setChecked(false);
                        tvTime.setText(String.format("진행 시간 : " + timeFormat.format(0)));

                    }
                });
            }
        }.start();
    }


    // Service

    public void serviceStart(View v) {

        Intent intent = new Intent(this, MusicService.class);

        intent.putExtra("song", R.raw.song1);
        startService(intent);

//        bindService(intent, conn, Context.BIND_AUTO_CREATE);  // for bind service

        Log.i("hwang", "startService()");
    }

    public void serviceStop(View v) {
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);

//        unbindService(conn);   // for bind service

        Log.i("hwang", "stopService()");

    }

    public void servicePlaySong(View v){

        if (isService) {
            ms.play(R.raw.song1);
            makeThreadBind();
        }

    }


    void makeThreadBind() {
        new Thread() {
            public void run() {
                // 음악이 계속 작동 중이라면
                final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                final MediaPlayer mp = ms.getMP();
                if (mp == null) return;
                seekMP3.setMax(mp.getDuration()); // 음악의 시간을 최대로 설정

                while (mp.isPlaying()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            seekMP3.setProgress(mp.getCurrentPosition());  // runOnUIThread 에서 안해도 no error.
                            tvTime.setText(String.format("진행 시간 : " + timeFormat.format(mp.getCurrentPosition())));
                        }
                    });

                    SystemClock.sleep(100);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekMP3.setProgress(0);
                        tvTime.setText(String.format("진행 시간 : " + timeFormat.format(0)));


                    }
                });


            }
        }.start();
    }

}