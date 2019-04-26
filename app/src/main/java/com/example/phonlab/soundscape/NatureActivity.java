package com.example.phonlab.soundscape;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class NatureActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    boolean repeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nature_layout);

        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        SwitchCompat loopToggle = findViewById(R.id.loop_toggle);
        loopToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(NatureActivity.this, "Loop is on", Toast.LENGTH_SHORT).show();
                    repeat = true;
                } else {
                    Toast.makeText(NatureActivity.this, "Loop is off", Toast.LENGTH_SHORT).show();
                    repeat = false;
                }
            }
        });
    }

    public void playBeach(View view) {
        requestAndImplementAudioFocus(R.raw.beach);
    }

    public void playCity(View view) {
        requestAndImplementAudioFocus(R.raw.city);
    }

    public void playRainforest(View view) {
        requestAndImplementAudioFocus(R.raw.rainforest);
    }

    public void playStorm(View view) {
        requestAndImplementAudioFocus(R.raw.storm);
    }

    //Listener called when a media file finishes playing
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if(repeat){
                mMediaPlayer.seekTo(0);
                mMediaPlayer.start();
            } else {
                releaseMediaPlayer();
            }
        }
    };

    //Release the MediaPlayer object and its resources
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
        mMediaPlayer = null;
    }

    //Takes in a raw file and then requests audioFocus.  If focus is granted it creates a MediaPlayer object with the passed in raw file
    public void requestAndImplementAudioFocus(int resourceID){
        releaseMediaPlayer();

        int result;
        result = mAudioManager.requestAudioFocus(mOnAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            mMediaPlayer = MediaPlayer.create(this, resourceID);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    //This listener is called when the audiofocus changes and then either starts pauses or deletes audio
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };








}
