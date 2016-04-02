package com.audiodemo;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioM extends AppCompatActivity {

    private Button vibrate;
    private Button ring;
    private Button silent;
    private Button mode;
    private Button ringUp;
    private Button ringDown;
    private Button currentStreamUp;
    private Button currentStreamDown;
    private Button setStream;
    private Button musicActive;
    private Button next;
    private AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_manager);

        vibrate = (Button) findViewById(R.id.vibrate_button);
        ring = (Button) findViewById(R.id.ring_button);
        silent = (Button) findViewById(R.id.silent_button);
        mode = (Button) findViewById(R.id.get_mode__button);
        ringUp = (Button) findViewById(R.id.plus_button);
        ringDown = (Button) findViewById(R.id.minus_button);
        currentStreamUp = (Button) findViewById(R.id.stream_up_button);
        currentStreamDown = (Button) findViewById(R.id.stream_down_button);
        setStream = (Button) findViewById(R.id.set_stream_button);
        musicActive = (Button) findViewById(R.id.headset_button);
        next = (Button) findViewById(R.id.next);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                Toast.makeText(AudioM.this, "Mode set to VIBRATION", Toast.LENGTH_SHORT).show();
            }
        });

        ring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(AudioM.this, "Mode set to NORMAL (RING)", Toast.LENGTH_SHORT).show();
            }
        });

        silent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Toast.makeText(AudioM.this, "Mode set to SILENT", Toast.LENGTH_SHORT).show();
            }
        });

        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mod = audioManager.getRingerMode();
                int stream = audioManager.getStreamVolume(mod);

                if(mod == AudioManager.RINGER_MODE_VIBRATE){
                    Toast.makeText(AudioM.this, "Current mode: VIBRATE", Toast.LENGTH_SHORT).show();
                }

                if(mod == AudioManager.RINGER_MODE_NORMAL){
                    Toast.makeText(AudioM.this, "Current mode: RING", Toast.LENGTH_SHORT).show();
                }

                if(mod == AudioManager.RINGER_MODE_SILENT){
                    Toast.makeText(AudioM.this, "Current mode: SILENT", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ringUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, 1);
            }
        });

        ringDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });

        currentStreamUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }
        });

        currentStreamDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });

        setStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), AudioManager.FLAG_PLAY_SOUND);
            }
        });

        musicActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AudioM.this, "Music: " + audioManager.isMusicActive(), Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AudioM.this, CaptureActivity.class);
                startActivity(intent);
            }
        });


    }
}
