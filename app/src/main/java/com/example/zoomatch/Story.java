package com.example.zoomatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Story extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Button tapToSkip = findViewById(R.id.tapToSkip);
        VideoView videoView = findViewById(R.id.animation_video);
        //videoView.setVideoPath();
        videoView.start();

        tapToSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Story.this, Gameplay.class);
                startActivity(intent);
            }
        });
    }
}