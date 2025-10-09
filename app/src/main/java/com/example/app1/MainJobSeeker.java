package com.example.app1;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainJobSeeker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_job_seeker);

        LinearLayout optionsSalary = findViewById(R.id.optionsSalary);
        LinearLayout optionsCity = findViewById(R.id.optionsCity);

        findViewById(R.id.cardSalary).setOnClickListener(v -> {
            if (optionsSalary.getVisibility() == View.GONE) {
                optionsSalary.setVisibility(View.VISIBLE);
            } else {
                optionsSalary.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.cardCity).setOnClickListener(v -> {
            if (optionsCity.getVisibility() == View.GONE) {
                optionsCity.setVisibility(View.VISIBLE);
            } else {
                optionsCity.setVisibility(View.GONE);
            }
        });
    }
}
