package com.example.quickcashcsci3130g_11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RatingsAdapter extends RecyclerView.Adapter<RatingsAdapter.RatingViewHolder> {

    private List<Rating> mRatingsList;

    public RatingsAdapter() {
        mRatingsList = new ArrayList<>();
    }

    public void setRatingsList(List<Rating> ratingsList) {
        mRatingsList = ratingsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rating, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        Rating rating = mRatingsList.get(position);

        holder.ratingValueTextView.setText("Rating: " + rating.getRatingValue());
        holder.commentsTextView.setText("Comments: " + rating.getComments());
    }

    @Override
    public int getItemCount() {
        return mRatingsList.size();
    }

    static class RatingViewHolder extends RecyclerView.ViewHolder {
        TextView ratingValueTextView;
        TextView commentsTextView;

        RatingViewHolder(View itemView) {
            super(itemView);
            ratingValueTextView = itemView.findViewById(R.id.ratingValueTextView);
            commentsTextView = itemView.findViewById(R.id.commentsTextView);
        }
    }
}
