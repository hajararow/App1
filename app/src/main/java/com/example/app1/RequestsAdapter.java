package com.example.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    ArrayList<RequestModel> list;
    Context context;

    public RequestsAdapter(ArrayList<RequestModel> list, Context context) {
        this.list = list;
        this.context = context;
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

        holder.specificJob.setText("תפקיד: " + model.specificJob);
        holder.city.setText("מיקום: " + model.city);
        holder.hourlyRate.setText("שכר לשעה: " + model.hourlyRate);
        holder.age.setText("פרטים נוספים: " + model.age);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView specificJob, city, hourlyRate, age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specificJob = itemView.findViewById(R.id.text_specific_job);
            city = itemView.findViewById(R.id.text_city);
            hourlyRate = itemView.findViewById(R.id.edittext_hourly_rate);
            age = itemView.findViewById(R.id.text_age);
        }
    }
}
