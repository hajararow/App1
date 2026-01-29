package com.example.app1;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

// استورد RequestsAdapter و RequestModel حسب مكان تواجدهم في مشروعك
import com.example.app1.RequestsAdapter;
import com.example.app1.RequestModel;


public class MyRequestsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RequestsAdapter adapter;
    ArrayList<RequestModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("הבקשות שלי");

        recyclerView = findViewById(R.id.recycler_requests);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RequestsAdapter(list, this);
        recyclerView.setAdapter(adapter);

        loadRequestsFromFirebase();
    }

    private void loadRequestsFromFirebase() {
        FirebaseFirestore.getInstance()
                .collection("Requests")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query) {
                        RequestModel model = doc.toObject(RequestModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}
