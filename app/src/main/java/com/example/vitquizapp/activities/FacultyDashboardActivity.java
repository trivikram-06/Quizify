package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyDashboardActivity extends AppCompatActivity {

    private Button createQuizButton, viewReportsButton, logoutButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        createQuizButton = findViewById(R.id.createQuizButton);
        viewReportsButton = findViewById(R.id.viewReportsButton);
        logoutButton = findViewById(R.id.logoutButton);

        // ✅ Null Check
        if (createQuizButton == null || viewReportsButton == null || logoutButton == null) {
            Toast.makeText(this, "Error: Button not found in XML!", Toast.LENGTH_LONG).show();
            return; // 🔥 Stop execution to prevent crash
        }

        // Navigate to Create Quiz Activity
        createQuizButton.setOnClickListener(v -> {
            startActivity(new Intent(FacultyDashboardActivity.this, CreateQuizActivity.class));
        });

        // Navigate to View Reports Activity
        viewReportsButton.setOnClickListener(v -> {
            startActivity(new Intent(FacultyDashboardActivity.this, ViewReportsActivity.class));
        });

        Button btnViewQuizzes = findViewById(R.id.btnViewQuizzes);
        btnViewQuizzes.setOnClickListener(v -> {
            startActivity(new Intent(FacultyDashboardActivity.this, QuizListActivity.class));
        });

        Button viewReportsButton = findViewById(R.id.viewReportsButton);
        viewReportsButton.setOnClickListener(v -> {
            Intent intent = new Intent(FacultyDashboardActivity.this, ViewReportsActivity.class);
            startActivity(intent);
        });



        // Logout Functionality
        logoutButton.setOnClickListener(v -> logoutUser());
    }


    private void logoutUser() {
        auth.signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FacultyDashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
