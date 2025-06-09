package com.example.vitquizapp.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private LinearLayout resultContainer;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreText = findViewById(R.id.scoreText);
        resultContainer = findViewById(R.id.resultContainer);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        ArrayList<String> questions = getIntent().getStringArrayListExtra("questions");
        ArrayList<String> userAnswers = getIntent().getStringArrayListExtra("userAnswers");
        ArrayList<String> correctAnswers = getIntent().getStringArrayListExtra("correctAnswers");

        scoreText.setText("🎯 You scored: " + score + " / " + total);

        for (int i = 0; i < questions.size(); i++) {
            String q = questions.get(i);
            String ua = userAnswers.get(i);
            String ca = correctAnswers.get(i);

            boolean isCorrect = ua.equals(ca);

            TextView questionView = new TextView(this);
            questionView.setText("Q" + (i + 1) + ": " + q);
            questionView.setTextSize(16);
            questionView.setPadding(0, 20, 0, 5);

            TextView userAnswerView = new TextView(this);
            userAnswerView.setText("Your Answer: " + ua);
            userAnswerView.setTextColor(isCorrect ? getColor(R.color.green) : getColor(R.color.red));

            TextView correctAnswerView = new TextView(this);
            correctAnswerView.setText("Correct Answer: " + ca);
            correctAnswerView.setTextColor(getColor(R.color.black));

            resultContainer.addView(questionView);
            resultContainer.addView(userAnswerView);
            resultContainer.addView(correctAnswerView);
        }
    }
}
