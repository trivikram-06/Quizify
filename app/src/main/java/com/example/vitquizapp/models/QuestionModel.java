package com.example.vitquizapp.models;
import java.util.List;

public class QuestionModel {
    private String questionText;
    private List<String> options;
    private String correctOption;

    // Empty constructor for Firebase
    public QuestionModel() {}

    public QuestionModel(String questionText, List<String> options, String correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}
