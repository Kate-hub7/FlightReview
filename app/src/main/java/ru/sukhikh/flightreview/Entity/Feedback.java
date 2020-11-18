package ru.sukhikh.flightreview.Entity;

import androidx.annotation.NonNull;

public class Feedback{

    private final String feedbackStr;

    public Feedback(@NonNull final String feedback){
        this.feedbackStr = feedback;
    }

    public String getFeedbackStr() {
        return feedbackStr;
    }

}
