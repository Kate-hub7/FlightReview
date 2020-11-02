package ru.sukhikh.flightreview.Entity;

import java.text.DecimalFormat;

import ru.sukhikh.flightreview.Parameter;

public class Rating {

    private final Parameter parameter;
    private  int rating;
    private final boolean required;
    private boolean foodChecked;

    public Rating(Parameter parameter, int rating, boolean required) {
        this.parameter = parameter;
        this.rating = rating;
        this.required = required;
        this.foodChecked = false;
    }

    public boolean isFoodChecked() {
        return foodChecked;
    }

    public void setFoodChecked(boolean foodChecked) {
        this.foodChecked = foodChecked;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public int getRating() {
        return rating;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        DecimalFormat df;
        if(parameter==Parameter.FLIGHT){
            df = new DecimalFormat("0.0");
            return df.format(Double.valueOf(rating))+" "+parameter.getName();
        }

        return Double.valueOf(rating)+" "+parameter.getName();
    }
}
