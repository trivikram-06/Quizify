package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class LoginActivity extends AppCompatActivity {
    Button studentLoginBtn, facultyLoginBtn, studentSignupBtn, facultySignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentLoginBtn = findViewById(R.id.studentLoginBtn);
        facultyLoginBtn = findViewById(R.id.facultyLoginBtn);
        studentSignupBtn = findViewById(R.id.studentSignupBtn);
        facultySignupBtn = findViewById(R.id.facultySignupBtn);

        // ✅ Redirect to Student Login Page
        studentLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, StudentLoginActivity.class);
            startActivity(intent);
        });

        // ✅ Redirect to Faculty Login Page (Fixed)
        facultyLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FacultyLoginActivity.class);
            startActivity(intent);
        });

        // ✅ Redirect to Student Signup Page
        studentSignupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, StudentSignupActivity.class);
            startActivity(intent);
        });

        // ✅ Redirect to Faculty Signup Page
        facultySignupBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FacultySignupActivity.class);
            startActivity(intent);
        });
    }
}
