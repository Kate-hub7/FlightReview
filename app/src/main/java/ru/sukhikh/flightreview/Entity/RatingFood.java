package ru.sukhikh.flightreview.Entity;

public class RatingFood {

    private final Rating rating;
    private final boolean foodChecked;

    public RatingFood(Rating rating, boolean foodChecked) {
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
