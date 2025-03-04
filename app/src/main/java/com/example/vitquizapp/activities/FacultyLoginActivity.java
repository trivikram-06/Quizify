package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyLoginActivity extends AppCompatActivity {

    private EditText facultyEmail, facultyPassword;
    private Button facultyLoginButton;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        facultyEmail = findViewById(R.id.facultyLoginEmail);
        facultyPassword = findViewById(R.id.facultyLoginPassword);
        facultyLoginButton = findViewById(R.id.facultyLoginButton);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        facultyLoginButton.setOnClickListener(v -> loginFaculty());
    }

    private void loginFaculty() {
        String email = facultyEmail.getText().toString().trim();
        String password = facultyPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            facultyEmail.setError("Enter Email");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            facultyPassword.setError("Enter Password");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(FacultyLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(FacultyLoginActivity.this, FacultyActivity.class));
                        finish();
                    } else {
                        Toast.makeText(FacultyLoginActivity.this, "Login Failed! Check Credentials", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }
}
