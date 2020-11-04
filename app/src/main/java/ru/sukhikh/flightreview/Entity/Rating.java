package ru.sukhikh.flightreview.Entity;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

import ru.sukhikh.flightreview.Enum.Parameter;


public class Rating {

    private final Parameter parameter;
    private final int rating;
    private final boolean required;

    public Rating(@NonNull final Parameter parameter,
                  @NonNull final int rating,
                  @NonNull final boolean required) {
        this.parameter = parameter;
        this.rating = rating;
        this.required = required;
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

    public Parameter getParameter() {
        return parameter;
    }

    public int getRating() {
        return rating;
    }

    public boolean isRequired() {
        return required;
    }

}
