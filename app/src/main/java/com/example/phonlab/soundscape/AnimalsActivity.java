package com.example.phonlab.soundscape;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AnimalsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    AudioFocusRequest mFocusRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animals_layout);

        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void playDog(View view){
        requestAndImplementAudioFocus(R.raw.dog_bark);
    }

    public void playCat(View view){
        requestAndImplementAudioFocus(R.raw.cat_meow);
    }

    public void playCow(View view){
        requestAndImplementAudioFocus(R.raw.cow_moo);
    }

    public void playPig(View view){
        requestAndImplementAudioFocus(R.raw.pig_snort);
    }

    public void playHorse(View view){
        requestAndImplementAudioFocus(R.raw.horse_neigh);
    }

    public void playRooster(View view){
        requestAndImplementAudioFocus(R.raw.rooster_crow);
    }

    public void requestAndImplementAudioFocus(int resourceID){
        releaseMediaPlayer();

        int result;

        if(Build.VERSION.SDK_INT >= 26){
            mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setOnAudioFocusChangeListener(mOnAudioFocusChange)
                    .build();

            result = mAudioManager.requestAudioFocus(mFocusRequest);
        } else {
            result = mAudioManager.requestAudioFocus(mOnAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }

        if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            mMediaPlayer = MediaPlayer.create(this, resourceID);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
    }

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
                if(Build.VERSION.SDK_INT >= 26){
                    mAudioManager.abandonAudioFocusRequest(mFocusRequest);
                } else {
                    mAudioManager.abandonAudioFocus(mOnAudioFocusChange);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){
            mMediaPlayer.release();
        }
    };

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
        }
        mMediaPlayer = null;
    }

}
