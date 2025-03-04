package com.example.vitquizapp.models;

import com.example.vitquizapp.models.QuestionModel;

import java.util.List;

public class QuizModel {
    public String title;
    public List<QuestionModel> questions;

    public QuizModel() { }

    public QuizModel(String title, List<QuestionModel> questions) {
        this.title = title;
        this.questions = questions;
    }
}
