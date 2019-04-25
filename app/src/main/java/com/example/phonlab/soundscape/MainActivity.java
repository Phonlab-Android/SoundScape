package com.example.phonlab.soundscape;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchAnimals(View view) {
        Intent animalIntent = new Intent(this, AnimalsActivity.class);
        startActivity(animalIntent);
    }

    public void launchNature(View view) {
        Intent natureIntent = new Intent(this, NatureActivity.class);
        startActivity(natureIntent);
    }

    public void launchMusic(View view) {
        Intent musicIntent = new Intent(this, MusicActivity.class);
        startActivity(musicIntent);
    }

    public void launchMedieval(View view) {
        Intent medievalIntent = new Intent(this, MedievalActivity.class);
        startActivity(medievalIntent);
    }

    public void launchSpace(View view) {
        Intent spaceIntent = new Intent(this, SpaceActivity.class);
        startActivity(spaceIntent);
    }

    public void launchYourOwn(View view) {
        Intent yourOwnIntent = new Intent(this, YourOwnActivity.class);
        startActivity(yourOwnIntent);
    }
}

