package com.example.quickcashcsci3130g_11;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    Context context;
    private List<EmployeeProfile> mEmployeeList;
    private RefreshList refreshList;
    private LayoutInflater mInflater;
    private DatabaseReference mDatabase;

    public EmployeeAdapter(List<EmployeeProfile> EmployeeList) {
        mEmployeeList = EmployeeList;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater =LayoutInflater.from(parent.getContext());
        return new EmployeeViewHolder(mInflater.inflate(R.layout.activity_display_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeProfile user = mEmployeeList.get(position);
        holder.name.setText(user.getEmployeeName());
        holder.email.setText(user.getEmployeeEmail());
        holder.location.setText(user.getEmployeeLocation());

        boolean isFavourite = user.getEmployeeisFavourite();
        if (isFavourite) {
            holder.isEmployeeFavourite.setImageResource(R.drawable.ic_bookmark_active); // Set your favorite icon resource
        } else {
            holder.isEmployeeFavourite.setImageResource(R.drawable.ic_bookmark_inactive); // Set your not favorite icon resource
        }

        holder.isEmployeeFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavourite) {
                    mDatabase.child("favourite").setValue(user.getEmployeeKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("users").child(user.getEmployeeKey()).child("isFavourite").setValue(false)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(mInflater.getContext(), "Favourite Removed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
                } else {
                    DatabaseReference favouriteReference = FirebaseDatabase.getInstance().getReference().child("Favourite");
                    Map<String, Object> favourite = new HashMap<>();
                    favourite.put("favouriteKey", user.getEmployeeKey());

                    favouriteReference.setValue(favourite).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("users").child(user.getEmployeeKey()).child("isFavourite").setValue(true)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            if (refreshList != null) {
                                                refreshList.RefreshData();
                                            }
                                            Toast.makeText(mInflater.getContext(), "Favourite Added", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            }
        });

    }
    public void OnRefreshListListener(RefreshList refreshList){
        this.refreshList = refreshList;
    }
    public interface RefreshList{
        void RefreshData();
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }


    public static class EmployeeViewHolder extends  RecyclerView.ViewHolder {
        TextView name, email, location;
       ImageView isEmployeeFavourite;

        public EmployeeViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.EmployeeName);
            email = itemView.findViewById(R.id.EmployeeEmail);
            isEmployeeFavourite = itemView.findViewById(R.id.isFavourite);

        }
    }
}