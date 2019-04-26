package com.example.phonlab.soundscape;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

public class MedievalActivity extends AppCompatActivity{

    //Declare our reference variables for the MediaPlayer and AudioManager objects
    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    AudioFocusRequest mFocusRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medieval_layout);

        //Instantiate the AudioManager object
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    //Each of our 5 image click methods calls requestAndImplementAudioFocus and passes in its raw file
    public void playDragon(View view){
        requestAndImplementAudioFocus(R.raw.dragon_roar);
    }

    //creates a random variable 0-2 each click, and depending on this variable calls the request with a different raw file
    public void playSwords(View view){
        Random r = new Random();
        int swordNoise = r.nextInt(3);
        if(swordNoise == 0){
            requestAndImplementAudioFocus(R.raw.sword_one);
        } else if (swordNoise == 1){
            requestAndImplementAudioFocus(R.raw.sword_two);
        } else {
            requestAndImplementAudioFocus(R.raw.sword_three);
        }
    }

    public void playBattleHorn(View view){
        requestAndImplementAudioFocus(R.raw.battle_horn);
    }

    public void playTavern(View view){
        requestAndImplementAudioFocus(R.raw.tavern);
    }

    public void playBlacksmith(View view){
        requestAndImplementAudioFocus(R.raw.blacksmith);
    }

    //Listener called when a media file finishes playing
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mediaPlayer){
            mMediaPlayer.release();
        }
    };

    //Release the MediaPlayer object and its resources
    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
        }
        mMediaPlayer = null;
    }

    //Takes in a raw file and then requests audioFocus.  If focus is granted it creates a MediaPlayer object with the passed in raw file
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

    //This Listener is called when the audiofocus changes and then etiher starts pauses or deletes audio
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

}