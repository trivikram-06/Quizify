package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class QuizListActivity extends AppCompatActivity {

    private ListView quizListView;
    private String[] quizNames = {"Java", "DSA"}; // List of available quizzes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        quizListView = findViewById(R.id.quizListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.quiz_list_item, R.id.quizItemText, quizNames);
        quizListView.setAdapter(adapter);

        // Handle quiz selection
        quizListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedQuiz = quizNames[position].toLowerCase(); // Convert to lowercase for Firestore
            Intent intent = new Intent(QuizListActivity.this, QuizActivity.class);
            intent.putExtra("quizId", selectedQuiz); // Pass quiz name (java or dsa)
            startActivity(intent);
        });
    }
}
