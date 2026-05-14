package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private ArrayList<RequestModel> list;
    private Context context;
    private OnRecyclerItemClickListener listener;

    public RequestsAdapter(ArrayList<RequestModel> list, Context context) {
        this.list = list;
        this.context = context;
        listener = (MyRequestsActivity)context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestModel model = list.get(position);

        holder.specificJob.setText("תפקיד: " + model.getSpecificJob());
        holder.city.setText("מיקום: " + model.getCity());
        holder.hourlyRate.setText("שכר לשעה: " + model.getHourlyRate());
        holder.age.setText("גיל העובד: " + model.getAge());

        // 👇 هذا هو الجزء الجديد
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RequestDetailsActivity.class);

            intent.putExtra("specificJob", model.getSpecificJob());
            intent.putExtra("city", model.getCity());
            intent.putExtra("rate", model.getHourlyRate());
            intent.putExtra("age", model.getAge());

            intent.putExtra("region", model.getRegion());
            intent.putExtra("jobType", model.getJobType());
            intent.putExtra("field", model.getField());
            intent.putExtra("info", model.getInfo());
            intent.putExtra("phone", model.getPhone());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView specificJob, city, hourlyRate, age;
        Button removePost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specificJob = itemView.findViewById(R.id.text_specific_job);
            city = itemView.findViewById(R.id.text_city);
            hourlyRate = itemView.findViewById(R.id.edittext_hourly_rate);
            age = itemView.findViewById(R.id.text_age);
            removePost = itemView.findViewById(R.id.remove_post);
            removePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if( position != RecyclerView.NO_POSITION ) {
                        listener.delete(position);
                    }
                }
            });


        }
    }
}
