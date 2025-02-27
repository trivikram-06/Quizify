package com.example.vitquizapp.activities;
import com.example.vitquizapp.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CreateQuizActivity extends AppCompatActivity {
    private TextInputEditText questionInput, option1, option2, option3, option4, correctAnswer;
    private Button addQuestionButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        questionInput = findViewById(R.id.questionInput);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        correctAnswer = findViewById(R.id.correctAnswer);
        addQuestionButton = findViewById(R.id.addQuestionButton);

        db = FirebaseFirestore.getInstance();

        addQuestionButton.setOnClickListener(v -> addQuestionToFirebase());
    }

    private void addQuestionToFirebase() {
        String questionText = questionInput.getText().toString();
        String correctOption = correctAnswer.getText().toString();
        Map<String, Object> question = new HashMap<>();
        question.put("questionText", questionText);
        question.put("options", Arrays.asList(option1.getText().toString(), option2.getText().toString(), option3.getText().toString(), option4.getText().toString()));
        question.put("correctOption", correctOption);

        db.collection("quizzes").document("quizID1").collection("questions")
                .add(question)
                .addOnSuccessListener(documentReference -> Toast.makeText(CreateQuizActivity.this, "Question Added!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(CreateQuizActivity.this, "Failed to Add Question!", Toast.LENGTH_SHORT).show());
    }
}