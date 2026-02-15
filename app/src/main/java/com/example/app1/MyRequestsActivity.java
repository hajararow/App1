package com.example.app1;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

// استورد RequestsAdapter و RequestModel حسب مكان تواجدهم في مشروعك


public class MyRequestsActivity extends AppCompatActivity implements OnRecyclerItemClickListener {

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
        FirebaseUser firebaseUser = FBRef.refAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        FBRef.refPosts.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if( task.isSuccessful() ){
                    DataSnapshot ds = task.getResult();
                    for(DataSnapshot dsPost : ds.getChildren()){
                        JobPost jobPost = dsPost.getValue(JobPost.class);
                        Log.d("BBBBBBBBB",jobPost.getPostKey()+" ");
                        RequestModel requestModel = new RequestModel(jobPost.getSpecificJob(),jobPost.getCity(),jobPost.getHourlyRate(), jobPost.getAge(),jobPost.getPostKey());
                        list.add(requestModel);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void delete(int position) {
        String uid = FBRef.refAuth.getUid();
        RequestModel requestModel = list.get(position);

        FBRef.refPosts.child(uid).child(requestModel.getPostKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    list.remove(position);
                    adapter.notifyItemRemoved(position);

                }

            }
        });

    }
}
