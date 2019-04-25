package com.example.phonlab.soundscape;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AnimalsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    AudioManager mAudioManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animals_layout);

        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void playDog(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.dog_bark);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };

    public void playCat(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.cat_meow);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    public void playCow(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.cow_moo);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    public void playPig(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.pig_snort);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    public void playHorse(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.horse_neigh);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    public void playRooster(View view) {
        releaseMediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.rooster_crow);
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mMediaPlayer.release();
        }
    };

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }

        mMediaPlayer = null;
    }

}
