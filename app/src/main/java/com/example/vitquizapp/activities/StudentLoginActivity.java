package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentLoginActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginButton.setOnClickListener(v -> loginStudent());
    }

    private void loginStudent() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            checkStudentAccount(user.getUid());
                        }
                    } else {
                        Toast.makeText(this, "Login Failed! Check Email & Password", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkStudentAccount(String userId) {
        DocumentReference docRef = db.collection("users")
                .document("students")
                .collection("studentAccounts")
                .document(userId);

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

                // 🔥 FIX: Ensure only one instance of QuizListActivity is created
                Intent intent = new Intent(StudentLoginActivity.this, QuizListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish(); // Close the login activity
            } else {
                Toast.makeText(this, "Student account not found!", Toast.LENGTH_SHORT).show();
                auth.signOut(); // Logout if not a student
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}
