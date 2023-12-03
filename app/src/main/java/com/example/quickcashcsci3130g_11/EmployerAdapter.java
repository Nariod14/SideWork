package com.example.quickcashcsci3130g_11;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.EmployerViewHolder> {
    Context context;
    private List<EmployerProfile> mEmployerList;

    public EmployerAdapter(List<EmployerProfile> EmployerList) {
        mEmployerList = EmployerList;
    }

    @NonNull
    @Override
    public EmployerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_display_profile, parent, false);
        return new EmployerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerViewHolder holder, int position) {
        EmployerProfile user = mEmployerList.get(position);
        holder.name.setText(user.getEmployerName());
        holder.email.setText(user.getEmployerEmail());
        holder.location.setText(user.getEmployerLocation());
    }

    @Override
    public int getItemCount() {
        return mEmployerList.size();
    }


    public static class EmployerViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, location;
        Button saveButton;

        public EmployerViewHolder(@NonNull View itemView) {
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
