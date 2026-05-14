package com.example.app1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder> {

    ArrayList<JobPost> list;

    public JobsAdapter(ArrayList<JobPost> list) {
        this.list = list;
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

        holder.favorite.setOnClickListener(v -> {

            if (holder.isLiked) {

                holder.favorite.setImageResource(R.drawable.not_interested_icon);
                holder.isLiked = false;

            } else {

                holder.favorite.setImageResource(R.drawable.interested_icon);
                holder.isLiked = true;

            }

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        ImageView favorite;

        boolean isLiked = false;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text_specific_job);
            text2 = itemView.findViewById(R.id.text_city);

            favorite = itemView.findViewById(R.id.image_favorite);
        }
    }
}