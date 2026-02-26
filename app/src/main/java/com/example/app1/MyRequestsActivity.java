package com.example.app1;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;


public class MyRequestsActivity extends AppCompatActivity implements OnRecyclerItemClickListener{

    RecyclerView recyclerView;
    RequestsAdapter adapter;
    ArrayList<RequestModel> list = new ArrayList<>();

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.NewRequest) {
            // إعادة تشغيل نفس النشاط
            Intent intent = new Intent(this, MainEmployer.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.MyRequests) {
            // الانتقال إلى صفحة הבקשות שלי
            Intent intent = new Intent(this, MyRequestsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.LogOut) {
            // تسجيل الخروج من Firebase
            FirebaseAuth.getInstance().signOut();

            // الانتقال إلى صفحة تسجيل الدخول ومنع الرجوع
            Intent intent = new Intent(this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // إخفاء العنوان
        getSupportActionBar().show(); // تأكد أن التولبار موجود لإظهار المينيو

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
        Log.d("AAAAAAAA",requestModel.getAge() + " "+requestModel.getPostKey());

        FBRef.refPosts.child(uid).child(requestModel.getPostKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }
}
