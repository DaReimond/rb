package com.example.blackjack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activity_game_over extends AppCompatActivity {

    //Objects
    private Button playAgainButton, titleScreenButton, shareButton, saveButton;
    private TextView scoreTextView;

    //Name Popup
    private AlertDialog.Builder nameDialogBuilder;
    private AlertDialog nameDialog;
    private Button saveNameButton, cancelButton;
    private EditText enterNameInput;
    private String nameString;

    //FireBase
    FirebaseFirestore database = FirebaseFirestore.getInstance();


    private void enterNamePopupDialog(){

    nameDialogBuilder = new AlertDialog.Builder(this);
    final View namePopupView = getLayoutInflater().inflate(R.layout.name_popup, null);

    saveNameButton =  (Button) namePopupView.findViewById(R.id.saveNameButton);
    cancelButton = (Button) namePopupView.findViewById(R.id.cancelNameButton);

    enterNameInput = (EditText) namePopupView.findViewById(R.id.enterNameInput);



    nameDialogBuilder.setView(namePopupView);
    nameDialog = nameDialogBuilder.create();
    nameDialog.show();
    nameDialog.setCancelable(false);

    cancelButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameDialog.dismiss();
        }
    });

    saveNameButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameString = enterNameInput.getText().toString();

            Map<String, Object> user = new HashMap<>();
            user.put("name", nameString);
            user.put("score", Blackjack.score);
            user.put("order", Blackjack.score*(-1));

            database.collection("Scores").add(user);
            Blackjack.score = 100;

            nameDialog.dismiss();
            openActivityTitleScreen();
            finish();
            return;

        }
    });
    }

    private void openActivityBlackjack() {
        Intent openBlackjack = new Intent(this, Blackjack.class);
        startActivity(openBlackjack);
        Blackjack.score = 100;
    }

    private void openActivityTitleScreen() {
        Intent openTitleScreen = new Intent(this, MainActivity.class);
        startActivity(openTitleScreen);
        Blackjack.score = 100;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("money", 100);
        myEdit.putInt("score", 100);

        myEdit.apply();

        titleScreenButton = (Button) findViewById(R.id.toTitleScreen);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        shareButton = (Button) findViewById(R.id.shareButton);
        saveButton = (Button) findViewById(R.id.saveButton);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        scoreTextView.setText("Score: "+Blackjack.score+"$");


        titleScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityTitleScreen();
                finish();
                return;
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBlackjack();
                finish();
                return;
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                String shareBody = "You Won't believe this, I've Got a NEW HIGHSCORE on Reimond's Blackjack!!! It's ";

                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                intent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody+scoreTextView.getText());

                startActivity(Intent.createChooser(intent, getString(R.string.share_using)));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNamePopupDialog();
            }
        });

    }


}