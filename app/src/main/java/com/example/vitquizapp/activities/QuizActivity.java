package com.example.vitquizapp.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.example.vitquizapp.models.QuestionModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<QuestionModel> questionList;
    private TextView questionText, timerText, resultText;
    private RadioGroup optionsGroup;
    private Button nextButton, prevButton, submitButton;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private String selectedCourse;
    private CountDownTimer countDownTimer;
    private static final long TIME_PER_QUESTION = 30000; // 30 seconds per question
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
        resultText = findViewById(R.id.resultText);
        optionsGroup = findViewById(R.id.optionsGroup);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        submitButton = findViewById(R.id.submitButton);

        selectedCourse = getIntent().getStringExtra("course");
        if (selectedCourse == null) selectedCourse = "java";

        loadQuestions(selectedCourse);

        nextButton.setOnClickListener(v -> moveToNextQuestion());
        prevButton.setOnClickListener(v -> moveToPreviousQuestion());
        submitButton.setOnClickListener(v -> showResults());

        optionsGroup.setOnCheckedChangeListener((group, checkedId) -> saveSelectedAnswer(checkedId));
    }

    private void loadQuestions(String course) {
        CollectionReference questionsRef = db.collection("quizzes").document(course).collection("questions");
        questionsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                questionList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    QuestionModel question = document.toObject(QuestionModel.class);
                    questionList.add(question);
                }
                if (!questionList.isEmpty()) {
                    currentQuestionIndex = 0;
                    showQuestion();
                } else {
                    questionText.setText("No questions found.");
                    nextButton.setEnabled(false);
                    prevButton.setEnabled(false);
                    submitButton.setEnabled(false);
                }
            } else {
                Log.e("Firestore", "Error getting questions: ", task.getException());
            }
        });
    }

    private void moveToNextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        } else {
            nextButton.setEnabled(false);
            submitButton.setVisibility(View.VISIBLE); // Show submit button after last question
        }
    }

    private void moveToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showQuestion();
        }
    }

    private void showQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            QuestionModel currentQuestion = questionList.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getQuestionText());
            optionsGroup.removeAllViews();

            for (String option : currentQuestion.getOptions()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(option);
                radioButton.setId(View.generateViewId());
                optionsGroup.addView(radioButton);
            }

            startTimer();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        timeLeftInMillis = TIME_PER_QUESTION;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerText.setText("⏳ Time's up!");
                moveToNextQuestion();
            }
        }.start();
    }

    private void updateTimerText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        timerText.setText(String.format(Locale.getDefault(), "Time: %d sec", seconds));
    }

    private void saveSelectedAnswer(int checkedId) {
        if (checkedId != -1) {
            RadioButton selectedRadioButton = findViewById(checkedId);
            selectedAnswers.put(currentQuestionIndex, selectedRadioButton.getText().toString());
        }
    }

    private void showResults() {
        int correctAnswers = 0;

        for (int i = 0; i < questionList.size(); i++) {
            String selectedAnswer = selectedAnswers.get(i);
            String correctAnswer = questionList.get(i).getCorrectOption();

            if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
                correctAnswers++;
            }
        }

        score = correctAnswers;
        questionText.setText("🎉 Quiz Completed!\nYour Score: " + score + "/" + questionList.size());

        optionsGroup.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        prevButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
    }
}
