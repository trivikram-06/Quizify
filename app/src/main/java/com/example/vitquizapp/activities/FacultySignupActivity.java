package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FacultySignupActivity extends AppCompatActivity {

    private EditText facultyName, facultyEmail, facultyPassword;
    private Button facultySignupButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_signup);

        facultyName = findViewById(R.id.facultySignupName);
        facultyEmail = findViewById(R.id.facultySignupEmail);
        facultyPassword = findViewById(R.id.facultySignupPassword);
        facultySignupButton = findViewById(R.id.facultySignupButton);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        facultySignupButton.setOnClickListener(v -> registerFaculty());
    }

    private void registerFaculty() {
        String name = facultyName.getText().toString().trim();
        String email = facultyEmail.getText().toString().trim();
        String password = facultyPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            facultyName.setError("Enter Name");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            facultyEmail.setError("Enter Email");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            facultyPassword.setError("Password must be at least 6 characters");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Firebase Authentication - Create User
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        DocumentReference docRef = firestore.collection("faculty").document(userId);

                        Map<String, Object> faculty = new HashMap<>();
                        faculty.put("name", name);
                        faculty.put("email", email);
                        faculty.put("userId", userId);

                        docRef.set(faculty).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Toast.makeText(FacultySignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FacultySignupActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(FacultySignupActivity.this, "Signup Failed! Try Again", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(FacultySignupActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }
}
