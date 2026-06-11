package com.example.app1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class favoritePostsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<JobPost> list;
    JobsAdapter adapter;

    ArrayList<String> favoriteIds = new ArrayList<>();

    // ✅ المنيو
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.jobseeker_menu, menu);
        return true;
    }

    // ✅ الضغط على عناصر المنيو
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.Job_search) {
            startActivity(new Intent(this, MainJobSeeker.class));
            finish();
            return true;
        }

        if (id == R.id.Favorite_jobs) {
            return true;
        }

        if (id == R.id.LogOut) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_posts);

        // ✅ Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ❌ حذفنا زر الرجوع
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new JobsAdapter(list);
        recyclerView.setAdapter(adapter);

        loadFavorites();
    }

    private void loadFavorites() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FBRef.refFavorites.child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        favoriteIds.clear();

                        for (DataSnapshot data : snapshot.getChildren()) {
                            favoriteIds.add(data.getKey());
                        }

                        loadJobs();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }

    private void loadJobs() {

        FBRef.refPosts.get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                list.clear();

                DataSnapshot ds = task.getResult();

                for (DataSnapshot userPosts : ds.getChildren()) {

                    for (DataSnapshot posts : userPosts.getChildren()) {

                        JobPost job = posts.getValue(JobPost.class);

                        if (job != null) {

                            job.setPostId(posts.getKey());

                            if (favoriteIds.contains(job.getPostId())) {
                                list.add(job);
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}
