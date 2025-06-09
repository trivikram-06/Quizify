package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.example.vitquizapp.adapters.QuestionAdapter;
import com.example.vitquizapp.models.Question;
import java.util.ArrayList;

public class StartQuizActivity extends AppCompatActivity {
    private TextView quizDetailsText;
    private ListView questionsListView;
    private Button btnSubmitQuiz;
    private ArrayList<Question> questionList;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        quizDetailsText = findViewById(R.id.quizDetailsText);
        questionsListView = findViewById(R.id.questionsListView);
        btnSubmitQuiz = findViewById(R.id.btnSubmitQuiz);

        // ✅ Get Quiz ID & Questions from Intent
        Intent intent = getIntent();
        questionList = intent.getParcelableArrayListExtra("questionList");

        if (questionList == null || questionList.isEmpty()) {
            Log.e("StartQuizActivity", "No questions received!");
            Toast.makeText(this, "No questions available!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Debug Log
        Log.d("StartQuizActivity", "Received Questions: " + questionList.size());

        // ✅ Set Adapter
        adapter = new QuestionAdapter(this, questionList);
        questionsListView.setAdapter(adapter);

        btnSubmitQuiz.setOnClickListener(v -> submitQuiz());
    }

    private void submitQuiz() {
        Toast.makeText(this, "Quiz Submitted!", Toast.LENGTH_SHORT).show();
    }
}
