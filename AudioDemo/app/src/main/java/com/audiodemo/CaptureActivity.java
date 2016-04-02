package com.audiodemo;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class CaptureActivity extends AppCompatActivity {
    Button record, play, stop;
    MediaRecorder myAudioRecorder;
    MediaPlayer mp;

    private String sourceToOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_capture_and_playback);

        sourceToOutput = Environment.getExternalStorageDirectory().getAbsolutePath() + "/rec_sound.3gp";

        myAudioRecorder = new MediaRecorder();

        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(sourceToOutput);

        mp = new MediaPlayer();


        record = (Button) findViewById(R.id.recButton);
        play = (Button) findViewById(R.id.playButton);
        stop = (Button) findViewById(R.id.stopButton);

        play.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);


        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAudioRecorder == null) {
                    myAudioRecorder = new MediaRecorder();
                    myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    myAudioRecorder.setOutputFile(sourceToOutput);
                }

                try {
                    myAudioRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAudioRecorder.start();
                v.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                play.setVisibility(View.INVISIBLE);
                Toast.makeText(CaptureActivity.this, "Recording from MIC...", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                record.setVisibility(View.VISIBLE);
                play.setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);

                Toast.makeText(CaptureActivity.this, "Recording stopped...", Toast.LENGTH_SHORT).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mp = new MediaPlayer();
                    mp.setDataSource(sourceToOutput);
                    mp.prepare();
                    mp.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                record.setVisibility(View.INVISIBLE);



            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mp != null){
                        if (!mp.isPlaying() && myAudioRecorder == null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    record.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                }
            }
        }).start();


    }
}

