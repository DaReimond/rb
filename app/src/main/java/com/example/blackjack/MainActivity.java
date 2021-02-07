package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Button leaderboardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leaderboardButton = findViewById(R.id.leaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivityLeaderboard();
                finish();
                return;
            }
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBlackjack();
            }
        });
    }

    private void openActivityLeaderboard() {
        Intent openLeaderboard = new Intent(this, activity_leaders.class);
        startActivity(openLeaderboard);
    }

    private void openActivityBlackjack() {
        Intent openBlackjack = new Intent(this, Blackjack.class);
        startActivity(openBlackjack);
    }
}