package com.example.vitquizapp.models;

public class ReportModel {
    private String email;
    private String quiz;
    private int score;

    public ReportModel() {}

    public ReportModel(String email, String quiz, int score) {
        this.email = email;
        this.quiz = quiz;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public String getQuiz() {
        return quiz;
    }

    public int getScore() {
        return score;
    }
}
