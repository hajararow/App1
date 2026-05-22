package com.example.app1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {

    ArrayList<JobPost> list;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    // نخزن المفضلات من Firebase
    HashSet<String> favoriteSet = new HashSet<>();

    public JobsAdapter(ArrayList<JobPost> list) {
        this.list = list;

        // ⭐ مهم جداً: تشغيل تحميل المفضلات
        loadFavorites();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_item, parent, false);

        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        JobPost job = list.get(position);

        holder.text1.setText(job.getSpecificJob());
        holder.text2.setText(job.getCity());
        holder.text3.setText(job.getHourlyRate());
        holder.text4.setText(job.getAge());

        String postId = job.getPostId();

        // ⭐ عرض القلب حسب Firebase (مش فقط local)
        boolean isFav = favoriteSet.contains(postId);
        holder.favorite.setImageResource(
                isFav ? R.drawable.interested_icon : R.drawable.not_interested_icon
        );

        holder.favorite.setOnClickListener(v -> {

            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            JobPost currentJob = list.get(pos);

            String userId = auth.getCurrentUser().getUid();
            String jobId = currentJob.getPostId();

            boolean currentlyFav = favoriteSet.contains(jobId);

            if (!currentlyFav) {

                FBRef.refFavorites
                        .child(userId)
                        .child(jobId)
                        .setValue(true);

                favoriteSet.add(jobId);

            } else {

                FBRef.refFavorites
                        .child(userId)
                        .child(jobId)
                        .removeValue();

                favoriteSet.remove(jobId);
            }

            notifyItemChanged(pos);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2, text3, text4;
        ImageView favorite;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text_specific_job);
            text2 = itemView.findViewById(R.id.text_city);
            text3 = itemView.findViewById(R.id.text_hourly_rate);
            text4 = itemView.findViewById(R.id.text_age);

            favorite = itemView.findViewById(R.id.image_favorite);
        }
    }

    // =========================
    // تحميل المفضلات من Firebase
    // =========================
    private void loadFavorites() {

        String userId = auth.getCurrentUser().getUid();

        FBRef.refFavorites.child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        favoriteSet.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            favoriteSet.add(ds.getKey());
                        }

                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}