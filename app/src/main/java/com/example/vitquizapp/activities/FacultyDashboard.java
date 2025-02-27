package com.example.vitquizapp.activities;

import com.example.vitquizapp.R;
import com.example.vitquizapp.activities.ViewReportsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FacultyDashboard extends AppCompatActivity {
    private Button createQuizButton, viewReportsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        createQuizButton = findViewById(R.id.createQuizButton);
        viewReportsButton = findViewById(R.id.viewReportsButton);

        createQuizButton.setOnClickListener(v -> {
            startActivity(new Intent(FacultyDashboard.this, CreateQuizActivity.class));
        });

        viewReportsButton.setOnClickListener(v -> {
            startActivity(new Intent(FacultyDashboard.this, ViewReportsActivity.class));
        });
    }
}
