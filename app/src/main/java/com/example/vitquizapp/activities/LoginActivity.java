package com.example.vitquizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vitquizapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout studentOptions, facultyOptions;
    private Button btnStudent, btnFaculty, btnStudentLogin, btnStudentSignup, btnFacultyLogin, btnFacultySignup;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // UI Components
        btnStudent = findViewById(R.id.btnStudent);
        btnFaculty = findViewById(R.id.btnFaculty);
        studentOptions = findViewById(R.id.studentOptions);
        facultyOptions = findViewById(R.id.facultyOptions);
        btnStudentLogin = findViewById(R.id.btnStudentLogin);
        btnStudentSignup = findViewById(R.id.btnStudentSignup);
        btnFacultyLogin = findViewById(R.id.btnFacultyLogin);
        btnFacultySignup = findViewById(R.id.btnFacultySignup);

        // Hide both options initially
        studentOptions.setVisibility(View.GONE);
        facultyOptions.setVisibility(View.GONE);

        // Show Student Options
        btnStudent.setOnClickListener(view -> {
            studentOptions.setVisibility(View.VISIBLE);
            facultyOptions.setVisibility(View.GONE);
        });

        // Show Faculty Options
        btnFaculty.setOnClickListener(view -> {
            facultyOptions.setVisibility(View.VISIBLE);
            studentOptions.setVisibility(View.GONE);
        });

        // Navigation Logic
        btnStudentLogin.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, StudentLoginActivity.class)));
        btnStudentSignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, StudentSignupActivity.class)));
        btnFacultyLogin.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, FacultyLoginActivity.class)));
        btnFacultySignup.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, FacultySignupActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            Log.d("LoginActivity", "✅ User is logged in: " + user.getUid());
            checkUserRole(user.getUid());
        } else {
            Log.d("LoginActivity", "🚪 No user logged in");
        }
    }

    private void checkUserRole(String userId) {
        Log.d("LoginActivity", "🔍 Checking Firestore for UserID: " + userId);

        db.collection("users").document(userId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            handleUserRole(document);
                        } else {
                            Log.e("LoginActivity", "❌ User role not found in Firestore!");
                            auth.signOut();
                            Toast.makeText(LoginActivity.this, "User role not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("LoginActivity", "❌ Firestore error: " + task.getException().getMessage());
                    }
                });
    }

    private void handleUserRole(@NonNull DocumentSnapshot document) {
        String role = document.getString("role");

        if (role != null) {
            Log.d("LoginActivity", "✅ User Role Found: " + role);

            Intent intent;
            if (role.equals("faculty")) {
                Log.d("LoginActivity", "🎓 Redirecting to Faculty Dashboard");
                intent = new Intent(LoginActivity.this, FacultyDashboardActivity.class);
            } else {
                Log.d("LoginActivity", "📚 Redirecting to Student Dashboard");
                intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
            }

            // ✅ Fix: Prevents going back to login page
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {
            Log.e("LoginActivity", "⚠️ Role field is missing in Firestore!");
            auth.signOut();
            Toast.makeText(this, "Role not found, contact admin", Toast.LENGTH_SHORT).show();
        }
    }
}
