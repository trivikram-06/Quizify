package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.resultText);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        resultText.setText("🎉 You scored: " + score + " / " + total);
    }
}
