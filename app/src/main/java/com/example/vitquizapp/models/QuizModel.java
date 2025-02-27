package com.example.vitquizapp.models;

import com.example.vitquizapp.models.Question;

import java.util.List;

public class QuizModel {
    public String title;
    public List<Question> questions;

    public QuizModel() { }

    public QuizModel(String title, List<Question> questions) {
        this.title = title;
        this.questions = questions;
    }
}
