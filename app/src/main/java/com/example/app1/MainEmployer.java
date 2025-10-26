package com.example.app1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainEmployer extends AppCompatActivity {

    private FirebaseFirestore db;
    private Spinner spinnerAge;
    private Spinner spinnerEmploymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_employer);

        db = FirebaseFirestore.getInstance();

        spinnerAge = findViewById(R.id.spinner_age);
        spinnerEmploymentType = findViewById(R.id.spinner_employment_type);

        loadAgesFromFirebase();
        loadEmploymentTypesFromFirebase();
    }

    private void loadAgesFromFirebase() {
        db.collection("ages")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> ages = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String age = doc.getString("name");
                        if (age != null) {
                            ages.add(age);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            ages
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAge.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "خطأ في تحميل الأعمار", Toast.LENGTH_SHORT).show()
                );
    }

    // تحميل أنواع التوظيف
    private void loadEmploymentTypesFromFirebase() {
        db.collection("employment_types")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> types = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String type = doc.getString("name");
                        if (type != null) {
                            types.add(type);
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            types
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmploymentType.setAdapter(adapter);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "خطأ في تحميل أنواع التوظيف", Toast.LENGTH_SHORT).show()
                );
    }
}
