package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import static com.example.app1.FBRef.refAuth;
import static com.example.app1.FBRef.refUsers;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2700; // 2.7 ثانية

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // تشغيل التأخير ثم التحقق من المستخدم على UI thread
        new Handler(Looper.getMainLooper()).postDelayed(this::checkUser, SPLASH_DELAY);
    }

    private void checkUser() {
        FirebaseUser currentUser = refAuth.getCurrentUser();

        if (currentUser != null) {
            // المستخدم مسجل مسبقًا → جلب البيانات من Realtime Database
            refUsers.child(currentUser.getUid()).get().addOnSuccessListener(snapshot -> {
                String accountType = snapshot.child("accountType").getValue(String.class);

                if ("Job Seeker".equals(accountType)) {
                    startActivity(new Intent(SplashActivity.this, MainJobSeeker.class));
                } else if ("Employer".equals(accountType)) {
                    startActivity(new Intent(SplashActivity.this, MainEmployer.class));
                } else {
                    // إذا لم يوجد نوع الحساب، نرسل المستخدم للتسجيل
                    startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                }
                finish();
            }).addOnFailureListener(e -> {
                // في حالة فشل جلب البيانات، نرسل للتسجيل
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                finish();
            });
        } else {
            // المستخدم لم يسجل الدخول بعد → نرسل للتسجيل
            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
            finish();
        }
    }
}
