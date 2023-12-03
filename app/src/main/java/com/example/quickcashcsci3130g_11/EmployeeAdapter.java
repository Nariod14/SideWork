package com.example.quickcashcsci3130g_11;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    Context context;
    private List<EmployeeProfile> mEmployeeList;

    public EmployeeAdapter(List<EmployeeProfile> EmployeeList) {
        mEmployeeList = EmployeeList;

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_display_profile,parent,false);
        return new EmployeeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeProfile user = mEmployeeList.get(position);
        holder.name.setText(user.getEmployeeName());
        holder.email.setText(user.getEmployeeEmail());
        holder.location.setText(user.getEmployeeLocation());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }


    public static class EmployeeViewHolder extends  RecyclerView.ViewHolder {
        TextView name, email, location;
        Button saveButton;

        public EmployeeViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.EmployeeName);
            email = itemView.findViewById(R.id.EmployeeEmail);
            saveButton = itemView.findViewById(R.id.saveProfile);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}