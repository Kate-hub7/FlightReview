package ru.sukhikh.flightreview.Entity;

import ru.sukhikh.flightreview.Parameter;

public class Rating {

    private final Parameter parameter;
    private  int rating;
    private final boolean required;

    public Rating(Parameter parameter, int rating, boolean required) {
        this.parameter = parameter;
        this.rating = rating;
        this.required = required;
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
}
