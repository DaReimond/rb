package com.example.blackjack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class activity_leaders extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);

        Query query = firebaseFirestore.collection("Scores")
                .orderBy("order");


        FirestoreRecyclerOptions<ScoreModel> options = new FirestoreRecyclerOptions.Builder<ScoreModel>()
                .setQuery(query, ScoreModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ScoreModel, ScoresViewHolder>(options) {
            @NonNull
            @Override
            public ScoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_item_layout, parent, false);
                return new ScoresViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ScoresViewHolder holder, int i, @NonNull ScoreModel model) {
                holder.playerName.setText(model.getName());
                holder.playerScore.setText(model.getScore()+"$");
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
        }

    private void openActivityTitleScreen() {
        Intent openTitleScreen = new Intent(this, MainActivity.class);
        startActivity(openTitleScreen);
    }

    public void backToTitleMenu(View view) {
        openActivityTitleScreen();
        finish();
        return;

    }

    private class ScoresViewHolder extends RecyclerView.ViewHolder {

        private TextView playerName;
        private TextView playerScore;


        public ScoresViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.playerNameTextView);
            playerScore = itemView.findViewById(R.id.playerScoreTextView);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
