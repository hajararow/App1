package com.example.app1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RequestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        // 🔙 Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        // 📦 استقبال البيانات
        String age = getIntent().getStringExtra("age");
        String region = getIntent().getStringExtra("region");
        String city = getIntent().getStringExtra("city");
        String jobType = getIntent().getStringExtra("jobType");
        String rate = getIntent().getStringExtra("rate");
        String field = getIntent().getStringExtra("field");
        String specificJob = getIntent().getStringExtra("specificJob");
        String info = getIntent().getStringExtra("info");
        String phone = getIntent().getStringExtra("phone");

        // 🔗 ربط
        TextView textAge = findViewById(R.id.text_age);
        TextView textRegion = findViewById(R.id.text_region);
        TextView textCity = findViewById(R.id.text_city);
        TextView textJobType = findViewById(R.id.text_job_type);
        TextView textRate = findViewById(R.id.text_rate);
        TextView textField = findViewById(R.id.text_field);
        TextView textSpecificJob = findViewById(R.id.text_specific_job);
        TextView textInfo = findViewById(R.id.text_info);
        TextView textPhone = findViewById(R.id.text_phone);

        // 📄 عرض
        textAge.setText("גיל העובד: " + age);
        textRegion.setText("אזור: " + region);
        textCity.setText("עיר: " + city);
        textJobType.setText("סוג משרה: " + jobType);
        textRate.setText("שכר: " + rate);
        textField.setText("תחום: " + field);
        textSpecificJob.setText("תפקיד: " + specificJob);
        textInfo.setText("מידע נוסף: " + info);
        textPhone.setText("טלפון: " + phone);
    }
}