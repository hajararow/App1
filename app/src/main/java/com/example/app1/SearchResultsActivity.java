package com.example.app1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    RecyclerView recyclerJobs;

    ArrayList<JobPost> list;

    JobsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        String age = intent.getStringExtra("Age");
        String region = intent.getStringExtra("Region");
        String city = intent.getStringExtra("City");
        String jobType = intent.getStringExtra("JobType");
        String jobField = intent.getStringExtra("JobField");
        String specificJob = intent.getStringExtra("SpecificJob");


        recyclerJobs = findViewById(R.id.recyclerJobs);

        recyclerJobs.setLayoutManager(
                new LinearLayoutManager(this)
        );

        list = new ArrayList<>();

        adapter = new JobsAdapter(list);

        recyclerJobs.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {
        FBRef.refPosts.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if( task.isSuccessful()){
                    list.clear();
                    DataSnapshot ds = task.getResult();
                    for (DataSnapshot userPosts : ds.getChildren()) {
                        for( DataSnapshot posts : userPosts.getChildren()){
                            JobPost job = posts.getValue(JobPost.class);
                            list.add(job);
                        }
                    }
                    adapter.notifyDataSetChanged();

                }

            }

        });





//        FBRef.refPosts.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                list.clear();
//
//                for (DataSnapshot data : snapshot.getChildren()) {
//
//                    JobPost job = data.getValue(JobPost.class);
//
//                    if (job != null) {
//
//                        list.add(job);
//                    }
//                }
//
//                adapter.notifyDataSetChanged();
//            }


 //       });
    }
}