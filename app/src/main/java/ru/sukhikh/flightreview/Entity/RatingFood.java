package ru.sukhikh.flightreview.Entity;

import androidx.annotation.NonNull;

public class RatingFood {

    private final Rating rating;
    private final boolean foodChecked;

    public RatingFood(@NonNull Rating rating,
                      @NonNull boolean foodChecked) {
        this.rating = rating;
        this.foodChecked = foodChecked;
    }

    @Override
    public String toString() {

        return Double.valueOf(rating.getRating())+" "+rating.getParameter().getName();
    }

    public Rating getRating() {
        return rating;
    }

    public boolean isFoodChecked() {
        return foodChecked;
    }
}
