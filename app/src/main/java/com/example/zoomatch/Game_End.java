package com.example.zoomatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game_End extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);

        int score = Integer.parseInt(getIntent().getStringExtra("final_score"));

        ImageView star_1, star_2, star_3, star_lose_1, star_lose_2, star_lose_3;
        TextView final_score = findViewById(R.id.final_score);
        TextView victory = findViewById(R.id.victory);
        TextView lose = findViewById(R.id.gameOver);
        Button play_again = findViewById(R.id.playAgain);

        star_1 = findViewById(R.id.star_1);
        star_2 = findViewById(R.id.star_2);
        star_3 = findViewById(R.id.star_3);
        star_lose_1 = findViewById(R.id.loseStar_1);
        star_lose_2 = findViewById(R.id.loseStar_2);
        star_lose_3 = findViewById(R.id.loseStar_3);


        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_End.this, Gameplay.class);
                startActivity(intent);
            }
        });

        if (score >= 15 && score < 20) {
            star_1.setVisibility(View.VISIBLE);
            star_lose_2.setVisibility(View.VISIBLE);
            star_lose_3.setVisibility(View.VISIBLE);
        } else if (score >= 20 && score < 30) {
            star_1.setVisibility(View.VISIBLE);
            star_2.setVisibility(View.VISIBLE);
            star_lose_3.setVisibility(View.VISIBLE);
        } else if (score >= 30) {
            star_1.setVisibility(View.VISIBLE);
            star_2.setVisibility(View.VISIBLE);
            star_3.setVisibility(View.VISIBLE);
        } else if(score < 30){
            star_lose_1.setVisibility(View.VISIBLE);
            star_lose_2.setVisibility(View.VISIBLE);
            star_lose_3.setVisibility(View.VISIBLE);
        }

        if (score >=30)
            victory.setVisibility(View.VISIBLE);
        else
            lose.setVisibility(View.VISIBLE);

        final_score.setText(String.valueOf(score));
    }
}