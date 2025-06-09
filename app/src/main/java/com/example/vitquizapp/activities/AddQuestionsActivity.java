package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.example.vitquizapp.models.Question;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddQuestionsActivity extends AppCompatActivity {

    private Button btnAddQuestion, btnPublishQuiz;
    private FirebaseFirestore db;
    private String quizId;

    private ArrayList<Question> questionList = new ArrayList<>();

    // ⬇️ These now come from CreateQuizActivity
    private String courseName, facultyName, startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        db = FirebaseFirestore.getInstance();

        // ✅ Get data passed from CreateQuizActivity
        courseName = getIntent().getStringExtra("courseName");
        facultyName = getIntent().getStringExtra("facultyName");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");

        btnAddQuestion = findViewById(R.id.btnAddQuestion);
        btnPublishQuiz = findViewById(R.id.btnPublishQuiz);

        btnAddQuestion.setOnClickListener(view -> {
            Intent intent = new Intent(AddQuestionsActivity.this, EnterQuestionActivity.class);
            intent.putParcelableArrayListExtra("questionList", questionList);
            startActivityForResult(intent, 1);
        });

        btnPublishQuiz.setOnClickListener(view -> publishQuiz());
        TextView txtQuizSummary = findViewById(R.id.txtQuizSummary);
        String summary = "Course: " + courseName + "\nFaculty: " + facultyName +
                "\nTime: " + startTime + " - " + endTime;
        txtQuizSummary.setText(summary);

    }

    private void publishQuiz() {
        if (questionList.isEmpty()) {
            Toast.makeText(this, "Please add at least one question before publishing.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Create map with passed quiz data
        Map<String, Object> quizData = new HashMap<>();
        quizData.put("courseName", courseName);
        quizData.put("facultyName", facultyName);
        quizData.put("startTime", startTime);
        quizData.put("endTime", endTime);
        quizData.put("questions", questionList);

        db.collection("quizzes")
                .add(quizData)
                .addOnSuccessListener(documentReference -> {
                    quizId = documentReference.getId();
                    Toast.makeText(this, "Quiz published successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back or navigate if needed
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error publishing quiz: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("AddQuestionsActivity", "Error publishing quiz", e);
                });
    }

    // 🔁 Optionally, handle question list returned from EnterQuestionActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            questionList = data.getParcelableArrayListExtra("questionList");
        }
    }
}
