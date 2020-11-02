package ru.sukhikh.flightreview.Entity;

public class Feedback{

    private  String feedbackStr;

    public Feedback(String feedback){
        this.feedbackStr = feedback;
    }

    public String getFeedbackStr() {
        return feedbackStr;
    }

    public void setFeedbackStr(String feedbackStr) {
        this.feedbackStr = feedbackStr;
    }
}
