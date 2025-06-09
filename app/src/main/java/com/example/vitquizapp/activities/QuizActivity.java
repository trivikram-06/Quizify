package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.example.vitquizapp.models.QuestionModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<QuestionModel> questionList;
    private TextView questionText, timerText, questionCountText;
    private RadioGroup optionsGroup;
    private Button nextButton, prevButton, submitButton;
    private int currentQuestionIndex = 0;
    private String selectedQuiz, studentEmail;
    private CountDownTimer countDownTimer;
    private static final long TIME_PER_QUESTION = 30000;
    private long timeLeftInMillis;

    // Store selected answers
    private Map<Integer, String> selectedAnswers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db = FirebaseFirestore.getInstance();
        questionList = new ArrayList<>();

        questionText = findViewById(R.id.questionText);
        timerText = findViewById(R.id.timerText);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        submitButton = findViewById(R.id.submitButton);
        questionCountText = findViewById(R.id.questionCountText);


        selectedQuiz = getIntent().getStringExtra("quizId");
        studentEmail = getIntent().getStringExtra("email");

        if (selectedQuiz == null) selectedQuiz = "java";
        if (studentEmail == null) studentEmail = "Unknown";

        loadQuestions(selectedQuiz);

        nextButton.setOnClickListener(v -> moveToNextQuestion());
        prevButton.setOnClickListener(v -> moveToPreviousQuestion());
        submitButton.setOnClickListener(v -> showResults());

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> saveSelectedAnswer(checkedId));
    }

    private void loadQuestions(String quizName) {
        db.collection("quizzes").document(quizName).collection("questions")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        questionList.clear();
                        for (var document : task.getResult()) {
                            QuestionModel question = document.toObject(QuestionModel.class);
                            questionList.add(question);
                        }
                        if (!questionList.isEmpty()) {
                            showQuestion();
                        } else {
                            questionText.setText("No questions found for this quiz.");
                        }
                    } else {
                        Log.e("Firestore", "Error fetching questions", task.getException());
                    }
                });
    }

    private void showQuestion() {
        if (countDownTimer != null) countDownTimer.cancel();

        if (currentQuestionIndex < questionList.size()) {
            QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
            // Show question count like Q3/10
            questionCountText.setText("Q" + (currentQuestionIndex + 1) + "/" + questionList.size());
            questionText.setText(currentQuestion.getQuestionText());

            optionsGroup.removeAllViews();

            for (String option : currentQuestion.getOptions()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                radioButton.setId(View.generateViewId());
                optionsGroup.addView(radioButton);
            }

            // Restore previous selection
            String selected = selectedAnswers.get(currentQuestionIndex);
            if (selected != null) {
                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) optionsGroup.getChildAt(i);
                    if (rb.getText().toString().equals(selected)) {
                        rb.setChecked(true);
                        break;
                    }
                }
            }

            // UI button visibility
            prevButton.setVisibility(currentQuestionIndex == 0 ? View.GONE : View.VISIBLE);
            nextButton.setVisibility(currentQuestionIndex == questionList.size() - 1 ? View.GONE : View.VISIBLE);
            submitButton.setVisibility(currentQuestionIndex == questionList.size() - 1 ? View.VISIBLE : View.GONE);

            startTimer();
        }
    }

    private void startTimer() {
        timeLeftInMillis = TIME_PER_QUESTION;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                timerText.setText(String.format(Locale.getDefault(), "Time: %d sec", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                moveToNextQuestion();
            }
        }.start();
    }

    private void moveToNextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        }
    }

    private void moveToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion();
        }
    }

    private void saveSelectedAnswer(int checkedId) {
        if (checkedId != -1) {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                selectedAnswers.put(currentQuestionIndex, selectedRadioButton.getText().toString());
            }
        }
    }

    private void showResults() {
        countDownTimer.cancel();
        int correctAnswers = 0;

        ArrayList<String> questions = new ArrayList<>();
        ArrayList<String> userAnswersList = new ArrayList<>();
        ArrayList<String> correctAnswersList = new ArrayList<>();

        for (int i = 0; i < questionList.size(); i++) {
            QuestionModel q = questionList.get(i);
            String userAnswer = selectedAnswers.getOrDefault(i, "No Answer");
            String correctAnswer = q.getCorrectOption();

            questions.add(q.getQuestionText());
            correctAnswersList.add(correctAnswer);
            userAnswersList.add(userAnswer);

            if (userAnswer.equals(correctAnswer)) {
                correctAnswers++;
            }
        }

        int score = correctAnswers;
        saveScoreToFirestore(score);

        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questionList.size());
        intent.putStringArrayListExtra("questions", questions);
        intent.putStringArrayListExtra("userAnswers", userAnswersList);
        intent.putStringArrayListExtra("correctAnswers", correctAnswersList);
        startActivity(intent);
        finish();
    }

    private void saveScoreToFirestore(int score) {
        Map<String, Object> scoreData = new HashMap<>();
        scoreData.put("email", studentEmail);
        scoreData.put("score", (long) score);
        scoreData.put("quiz", selectedQuiz);

        db.collection("quiz_results").add(scoreData)
                .addOnSuccessListener(doc -> Log.d("Firestore", "✅ Score saved"))
                .addOnFailureListener(e -> Log.e("Firestore", "❌ Error saving score", e));
    }
}
