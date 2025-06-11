package com.example.challenge3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnAddition, btnSubtraction, btnMultiplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sửa lỗi: Sử dụng đúng layout cho MainActivity
        setContentView(R.layout.activity_main);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        initializeViews();

        // Set button listeners
        setButtonListeners();
    }

    private void initializeViews() {
        btnAddition = findViewById(R.id.btn_Addition);
        btnSubtraction = findViewById(R.id.btn_Subtraction);
        btnMultiplication = findViewById(R.id.btn_Multiplication);

        // Set background for buttons nếu có drawable
        try {
            btnAddition.setBackgroundResource(R.drawable.button_background);
            btnSubtraction.setBackgroundResource(R.drawable.button_background);
            btnMultiplication.setBackgroundResource(R.drawable.button_background);
        } catch (Exception e) {
            // Log lỗi thay vì printStackTrace
            Log.w(TAG, "Could not set button background drawable", e);
        }
    }

    private void setButtonListeners() {
        btnAddition.setOnClickListener(v -> startMathActivity("addition"));

        btnSubtraction.setOnClickListener(v -> startMathActivity("subtraction"));

        btnMultiplication.setOnClickListener(v -> startMathActivity("multiplication"));
    }

    private void startMathActivity(String operation) {
        Intent intent = new Intent(MainActivity.this, AdditionActivity.class);
        intent.putExtra("operation", operation);
        startActivity(intent);
    }
}