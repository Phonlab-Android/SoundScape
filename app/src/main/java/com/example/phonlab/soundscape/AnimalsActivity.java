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
        requestAndImplementAudioFocus(R.raw.dog_bark);
    }

    public void playCat(View view) {
        requestAndImplementAudioFocus(R.raw.cat_meow);
    }

    public void playCow(View view) {
        requestAndImplementAudioFocus(R.raw.cow_moo);
    }

    public void playPig(View view) {
        requestAndImplementAudioFocus(R.raw.pig_snort);
    }

    public void playHorse(View view) {
        requestAndImplementAudioFocus(R.raw.horse_neigh);
    }

    public void playRooster(View view) {
        requestAndImplementAudioFocus(R.raw.rooster_crow);
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

    public void requestAndImplementAudioFocus(int resourceID) {
        releaseMediaPlayer();

        int result;
        result = mAudioManager.requestAudioFocus(mOnAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mMediaPlayer = MediaPlayer.create(this, resourceID);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        }
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

}
