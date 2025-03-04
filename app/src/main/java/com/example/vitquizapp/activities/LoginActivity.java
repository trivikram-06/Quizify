package com.example.vitquizapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private Button loginStudentButton, loginFacultyButton;
    private TextView signupRedirect;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase Auth instance
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginStudentButton = findViewById(R.id.loginStudentButton);
        loginFacultyButton = findViewById(R.id.loginFacultyButton);
        signupRedirect = findViewById(R.id.signupRedirect);

        // Student Login Button Click
        loginStudentButton.setOnClickListener(v -> loginUser("student"));

        // Faculty Login Button Click
        loginFacultyButton.setOnClickListener(v -> loginUser("faculty"));

        // Redirect to Signup Page
        signupRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Login Method for Student & Faculty
    private void loginUser(String userType) {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Redirect based on userType
                    if (userType.equals("student")) {
                        startActivity(new Intent(LoginActivity.this, QuizListActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, FacultyDashboard.class));
                    }
                    finish();
                }
            } else {
                Toast.makeText(this, "Login Failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "Login Error", task.getException());
            }
        });
    }
}
