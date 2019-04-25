package com.example.phonlab.soundscape;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AnimalsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animals_layout);
    }

    public void playDog(View view) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.dog_bark);
        mMediaPlayer.start();
    }

}
