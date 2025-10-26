package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import static com.example.app1.FBRef.refAuth;

public class LogInActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogIn;
    TextView tvSignUpRedirect;

    @Override
    protected void onStart() {
        super.onStart();

        boolean fromSignUp = getIntent().getBooleanExtra("fromSignUp", false);
        if (fromSignUp) return;

        FirebaseUser currentUser = refAuth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference refUsers = FBRef.refUsers;
            refUsers.child(currentUser.getUid()).get().addOnSuccessListener(snapshot -> {
                String accountType = snapshot.child("accountType").getValue(String.class);
                if ("Job Seeker".equals(accountType)) {
                    startActivity(new Intent(LogInActivity.this, MainJobSeeker.class));
                } else if ("Employer".equals(accountType)) {
                    startActivity(new Intent(LogInActivity.this, MainEmployer.class));
                }
                finish();
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogIn = findViewById(R.id.btnLogin);
        tvSignUpRedirect = findViewById(R.id.tvSignUpRedirect);

        btnLogIn.setOnClickListener(view -> loginUser());

        tvSignUpRedirect.setOnClickListener(view -> startActivity(new Intent(LogInActivity.this, SignUpActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        refAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = refAuth.getCurrentUser();
                        if (currentUser != null) {
                            FBRef.refUsers.child(currentUser.getUid()).get().addOnSuccessListener(snapshot -> {
                                String accountType = snapshot.child("accountType").getValue(String.class);
                                if ("Job Seeker".equals(accountType)) {
                                    startActivity(new Intent(LogInActivity.this, MainJobSeeker.class));
                                } else if ("Employer".equals(accountType)) {
                                    startActivity(new Intent(LogInActivity.this, MainEmployer.class));
                                }
                                finish();
                            });
                        }
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
