package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyActivity extends AppCompatActivity {
    private Button createQuizButton, viewReportsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        createQuizButton = findViewById(R.id.createQuizButton);
        viewReportsButton = findViewById(R.id.viewReportsButton);
        logoutButton = findViewById(R.id.logoutButton);

        createQuizButton.setOnClickListener(v -> startActivity(new Intent(FacultyActivity.this, CreateQuizActivity.class)));
        viewReportsButton.setOnClickListener(v -> startActivity(new Intent(FacultyActivity.this, ViewReportsActivity.class)));
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(FacultyActivity.this, LoginActivity.class));
            finish();
        });
    }
}
