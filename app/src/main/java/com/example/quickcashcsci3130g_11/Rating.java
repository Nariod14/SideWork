package com.example.quickcashcsci3130g_11;

public class Rating {
    private String userId;
    private float ratingValue;
    private String comments;

    public Rating() {}

    public Rating(String userId, float ratingValue, String comments) {
        this.userId = userId;
        this.ratingValue = ratingValue;
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}