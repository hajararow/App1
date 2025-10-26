package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;

import static com.example.app1.FBRef.refAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText etPassword, etEmail;
    Button btnSignUp;
    TextView tvLoginRedirect;
    RadioGroup rgAccountType;
    RadioButton rbJobSeeker, rbEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    public void init() {
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLoginRedirect = findViewById(R.id.tvSignUpRedirect);

        rgAccountType = findViewById(R.id.rgAccountType);
        rbJobSeeker = findViewById(R.id.rbJobSeeker);
        rbEmployer = findViewById(R.id.rbEmployer);

        btnSignUp.setOnClickListener(view -> registerUser());

        tvLoginRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            intent.putExtra("fromSignUp", true);
            startActivity(intent);
        });
    }
    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgAccountType.getCheckedRadioButtonId();
        final String accountType;

        if (selectedId == rbJobSeeker.getId()) {
            accountType = "Job Seeker";
        } else if (selectedId == rbEmployer.getId()) {
            accountType = "Employer";
        } else {
            Toast.makeText(this, "Please select account type", Toast.LENGTH_SHORT).show();
            return;
        }

        refAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String uid = refAuth.getCurrentUser().getUid();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("uid", uid);
                        userMap.put("email", email);
                        userMap.put("accountType", accountType);

                        FBRef.refUsers.child(uid).setValue(userMap)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                                    // الانتقال إلى صفحة تسجيل الدخول
                                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                                    intent.putExtra("fromSignUp", true);
                                    startActivity(intent);
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SignUpActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(SignUpActivity.this,
                                "Sign up failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
