package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class ResultActivity extends AppCompatActivity {
    private TextView resultText, messageText;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize UI elements
        resultText = findViewById(R.id.resultText);
        messageText = findViewById(R.id.messageText);
        homeButton = findViewById(R.id.homeButton);

        // Get score from Intent
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 1);

        // Display the score
        resultText.setText("Your Score: " + score + "/" + totalQuestions);

        // Set a message based on performance
        if (score == totalQuestions) {
            messageText.setText("Excellent! You're a quiz master! 🎉");
        } else if (score > totalQuestions / 2) {
            messageText.setText("Good job! Keep improving. 👍");
        } else {
            messageText.setText("Don't worry! Try again to improve. 😊");
        }

        // Go back to home screen
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}
