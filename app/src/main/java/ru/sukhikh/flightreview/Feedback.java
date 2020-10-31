package ru.sukhikh.flightreview;

import androidx.databinding.ObservableField;

public class Feedback{

   // private ObservableField<String> feedbackStr;
    private  String feedbackStr;

    public Feedback(String feedback){
        //feedbackStr.set(feedback);
        this.feedbackStr = feedback;
    }

    public String getFeedbackStr() {
        return feedbackStr;
    }

    public void setFeedbackStr(String feedbackStr) {
        this.feedbackStr = feedbackStr;
    }
}
