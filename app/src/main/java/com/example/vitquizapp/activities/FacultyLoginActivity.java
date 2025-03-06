package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FacultyLoginActivity extends AppCompatActivity {
    private EditText facultyEmail, facultyPassword;
    private Button facultyLoginButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        facultyEmail = findViewById(R.id.facultyEmail);
        facultyPassword = findViewById(R.id.facultyPassword);
        facultyLoginButton = findViewById(R.id.facultyLoginButton);

        facultyLoginButton.setOnClickListener(v -> loginFaculty());
    }

    private void loginFaculty() {
        String email = facultyEmail.getText().toString();
        String password = facultyPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            String userId = auth.getCurrentUser().getUid();

            db.collection("users").document("faculty").collection("facultyAccounts")
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Toast.makeText(FacultyLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                            // 🔥 Redirect to Faculty Dashboard Page After Login
                            Intent intent = new Intent(FacultyLoginActivity.this, FacultyDashboardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(FacultyLoginActivity.this, "Invalid Faculty Credentials!", Toast.LENGTH_SHORT).show();
                            auth.signOut(); // Log out if login is successful but data not found
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(FacultyLoginActivity.this, "Error fetching data!", Toast.LENGTH_SHORT).show());
        }).addOnFailureListener(e -> Toast.makeText(FacultyLoginActivity.this, "Login Failed! Check credentials.", Toast.LENGTH_SHORT).show());
    }
}
