package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.example.vitquizapp.models.Question;

public class EnterQuestionActivity extends AppCompatActivity {

    private EditText edtQuestion, edtOptionA, edtOptionB, edtOptionC, edtOptionD, edtCorrectAnswer;
    private Button btnSaveQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_question);

        // Initialize UI
        edtQuestion = findViewById(R.id.edtQuestion);
        edtOptionA = findViewById(R.id.edtOptionA);
        edtOptionB = findViewById(R.id.edtOptionB);
        edtOptionC = findViewById(R.id.edtOptionC);
        edtOptionD = findViewById(R.id.edtOptionD);
        edtCorrectAnswer = findViewById(R.id.edtCorrectAnswer);
        btnSaveQuestion = findViewById(R.id.btnSaveQuestion);

        btnSaveQuestion.setOnClickListener(view -> saveQuestion());
    }

    private void saveQuestion() {
        String question = edtQuestion.getText().toString().trim();
        String optionA = edtOptionA.getText().toString().trim();
        String optionB = edtOptionB.getText().toString().trim();
        String optionC = edtOptionC.getText().toString().trim();
        String optionD = edtOptionD.getText().toString().trim();
        String correctAnswer = edtCorrectAnswer.getText().toString().trim();

        if (question.isEmpty() || correctAnswer.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ Create Question Object
        Question newQuestion = new Question(question, optionA, optionB, optionC, optionD, correctAnswer);

        // ✅ Return result to AddQuestionsActivity
        Intent intent = new Intent();
        intent.putExtra("newQuestion", newQuestion);
        setResult(RESULT_OK, intent);
        finish();
    }
}
