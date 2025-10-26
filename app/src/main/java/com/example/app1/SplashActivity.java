package com.example.app1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import static com.example.app1.FBRef.refAuth;
import static com.example.app1.FBRef.refUsers;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        DelayThread delayThread = new DelayThread();
        Thread t = new Thread(delayThread);
        t.start();


    }

    private void checkUser() {
        FirebaseUser currentUser = refAuth.getCurrentUser();

        if (currentUser != null) {
            refUsers.child(currentUser.getUid()).get().addOnSuccessListener(snapshot -> {
                String accountType = snapshot.child("accountType").getValue(String.class);

                if ("Job Seeker".equals(accountType)) {
                    startActivity(new Intent(SplashActivity.this, MainJobSeeker.class));
                } else if ("Employer".equals(accountType)) {
                    startActivity(new Intent(SplashActivity.this, MainEmployer.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                }
                finish();
            }).addOnFailureListener(e -> {
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                finish();
            });
        } else {
            startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
        }
    }
    public class DelayThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(SPLASH_DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkUser();
        }
    }

}
