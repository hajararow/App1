package com.example.app1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

public class RequestDetailsFragment extends Fragment {

    public RequestDetailsFragment() {
        // لازم constructor فاضي
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_request_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔙 Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (getActivity() != null) {
            ((androidx.appcompat.app.AppCompatActivity) getActivity())
                    .setSupportActionBar(toolbar);
        }

        toolbar.setNavigationOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // 📦 استقبال البيانات
        Bundle args = getArguments();

        String age = args != null ? args.getString("age") : "";
        String region = args != null ? args.getString("region") : "";
        String city = args != null ? args.getString("city") : "";
        String jobType = args != null ? args.getString("jobType") : "";
        String rate = args != null ? args.getString("rate") : "";
        String field = args != null ? args.getString("field") : "";
        String specificJob = args != null ? args.getString("specificJob") : "";
        String info = args != null ? args.getString("info") : "";
        String phone = args != null ? args.getString("phone") : "";

        // 🔗 ربط
        TextView textAge = view.findViewById(R.id.text_age);
        TextView textRegion = view.findViewById(R.id.text_region);
        TextView textCity = view.findViewById(R.id.text_city);
        TextView textJobType = view.findViewById(R.id.text_job_type);
        TextView textRate = view.findViewById(R.id.text_rate);
        TextView textField = view.findViewById(R.id.text_field);
        TextView textSpecificJob = view.findViewById(R.id.text_specific_job);
        TextView textInfo = view.findViewById(R.id.text_info);
        TextView textPhone = view.findViewById(R.id.text_phone);

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