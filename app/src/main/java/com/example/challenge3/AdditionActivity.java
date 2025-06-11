package com.example.challenge3;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Random;

public class AdditionActivity extends AppCompatActivity {

    private TextView scoreValue, lifeValue, timerValue, questionText;
    private EditText answerInput;
    private Button submitButton, nextButton;

    private int score = 0;
    private int life = 3;
    private int timeLeft = 60;
    private int currentAnswer;
    private String operation = "addition";
    private final Random random = new Random();
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get operation type from intent
        String operationType = getIntent().getStringExtra("operation");
        if (operationType != null) {
            operation = operationType;
        }

        // Initialize views
        initializeViews();

        // Setup listeners
        setupListeners();

        // Generate first question
        generateQuestion();

        // Start timer
        startTimer();
    }

    private void initializeViews() {
        scoreValue = findViewById(R.id.scoreValue);
        lifeValue = findViewById(R.id.lifeValue);
        timerValue = findViewById(R.id.timerValue);
        questionText = findViewById(R.id.questionText);
        answerInput = findViewById(R.id.answerInput);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        // Update display
        updateDisplay();
    }

    private void setupListeners() {
        submitButton.setOnClickListener(v -> checkAnswer());

        nextButton.setOnClickListener(v -> {
            generateQuestion();
            answerInput.setText("");
            answerInput.setEnabled(true);
            submitButton.setEnabled(true);
        });
    }

    private void generateQuestion() {
        int num1 = random.nextInt(50) + 1; // 1-50
        int num2 = random.nextInt(50) + 1; // 1-50

        String questionStr = "";

        switch (operation) {
            case "addition":
                questionStr = num1 + " + " + num2 + " = ?";
                currentAnswer = num1 + num2;
                break;
            case "subtraction":
                // Ensure positive result
                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }
                questionStr = num1 + " - " + num2 + " = ?";
                currentAnswer = num1 - num2;
                break;
            case "multiplication":
                num1 = random.nextInt(12) + 1; // 1-12 for easier multiplication
                num2 = random.nextInt(12) + 1;
                questionStr = num1 + " × " + num2 + " = ?";
                currentAnswer = num1 * num2;
                break;
        }

        questionText.setText(questionStr);
    }

    private void checkAnswer() {
        String userInput = answerInput.getText().toString().trim();

        if (userInput.isEmpty()) {
            Toast.makeText(this, "Please enter an answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int userAnswer = Integer.parseInt(userInput);

            if (userAnswer == currentAnswer) {
                score += 10;
                Toast.makeText(this, "Correct! +10 points", Toast.LENGTH_SHORT).show();
                answerInput.setEnabled(false);
                submitButton.setEnabled(false);
            } else {
                life--;
                Toast.makeText(this, "Wrong! Correct answer: " + currentAnswer, Toast.LENGTH_SHORT).show();

                if (life <= 0) {
                    gameOver();
                    return;
                }
            }

            updateDisplay();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(timeLeft * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                timerValue.setText(String.valueOf(timeLeft));
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        }.start();
    }

    private void updateDisplay() {
        scoreValue.setText(String.valueOf(score));
        lifeValue.setText(String.valueOf(life));
        timerValue.setText(String.valueOf(timeLeft));
    }

    private void gameOver() {
        if (timer != null) {
            timer.cancel();
        }

        Intent intent = new Intent(AdditionActivity.this, ResultActivity.class);
        intent.putExtra("final_score", score);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}