package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class QuizListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Button javaButton = findViewById(R.id.javaQuizButton);
        Button dsaButton = findViewById(R.id.dsaQuizButton);

        javaButton.setOnClickListener(v -> startQuiz("java"));
        dsaButton.setOnClickListener(v -> startQuiz("dsa"));
    }

    private void startQuiz(String courseName) {
        Intent intent = new Intent(QuizListActivity.this, QuizActivity.class);
        intent.putExtra("course", courseName);
        startActivity(intent);
    }
}
