package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;

public class SignupActivity extends AppCompatActivity {

    private Button signupStudentButton, signupFacultyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupStudentButton = findViewById(R.id.signupStudentButton);
        signupFacultyButton = findViewById(R.id.signupFacultyButton);

        // Redirect to Student Signup
        signupStudentButton.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, StudentSignupActivity.class));
        });

        // Redirect to Faculty Signup
        signupFacultyButton.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, FacultySignupActivity.class));
        });
    }
}
