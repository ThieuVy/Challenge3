package com.example.challenge3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Prevent back button using modern OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing (disables back button)
                // Optional: Toast.makeText(ResultActivity.this, "Use the buttons below!", Toast.LENGTH_SHORT).show();
            }
        });

        // Get score from intent
        finalScore = getIntent().getIntExtra("final_score", 0);

        // Display result
        displayResult();

        // Button listeners
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        Button playAgainButton = findViewById(R.id.btn_play_again);
        Button exitButton = findViewById(R.id.btn_exit);

        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        exitButton.setOnClickListener(v -> finishAffinity());
    }

    @SuppressLint("SetTextI18n")
    private void displayResult() {
        TextView scoreDisplay = findViewById(R.id.scoreDisplay);
        scoreDisplay.setText("Score: " + finalScore);

        TextView congratsText = findViewById(R.id.congratsText);
        congratsText.setText(getResultMessage(finalScore));
    }

    private String getResultMessage(int score) {
        if (score >= 100) return "Excellent!";
        else if (score >= 70) return "Great Job!";
        else if (score >= 50) return "Good Work!";
        else if (score >= 30) return "Keep Practicing!";
        else return "Try Again!";
    }
}