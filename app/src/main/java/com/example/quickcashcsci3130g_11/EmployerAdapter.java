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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.EmployerViewHolder> {
    Context context;

    private RefreshList refreshList;
    private LayoutInflater mInflater;
    private DatabaseReference mDatabase;
    private List<EmployerProfile> mEmployerList;

    public EmployerAdapter(List<EmployerProfile> EmployerList) {
        mEmployerList = EmployerList;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public EmployerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater =LayoutInflater.from(parent.getContext());
        return new EmployerViewHolder(mInflater.inflate(R.layout.activity_display_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerViewHolder holder, int position) {
        EmployerProfile user = mEmployerList.get(position);
        holder.name.setText(user.getEmployerName());
        holder.email.setText(user.getEmployerEmail());
        holder.location.setText(user.getEmployerLocation());

        boolean isFavourite = user.getEmployerisFavourite();
        if (isFavourite) {
            holder.isEmployerFavourite.setImageResource(R.drawable.ic_bookmark_active); // Set your favorite icon resource
        } else {
            holder.isEmployerFavourite.setImageResource(R.drawable.ic_bookmark_inactive); // Set your not favorite icon resource
        }

        holder.isEmployerFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavourite) {
                    mDatabase.child("favourite").setValue(user.getEmployerKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("users").child(user.getEmployerKey()).child("isFavourite").setValue(false)
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
                    favourite.put("favouriteKey", user.getEmployerKey());

                    favouriteReference.setValue(favourite).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            mDatabase.child("users").child(user.getEmployerKey()).child("isFavourite").setValue(true)
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
        return mEmployerList.size();
    }


    public static class EmployerViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, location;
       ImageView isEmployerFavourite;

        public EmployerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.EmployeeName);
            email = itemView.findViewById(R.id.EmployeeEmail);
            isEmployerFavourite = itemView.findViewById(R.id.isFavourite);


        }
    }
}
