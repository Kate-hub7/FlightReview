package ru.sukhikh.flightreviewscreen;

public enum Parameter{
    FOOD ("How do you rate the food?"),
    FLIGHT (""),
    CREW ("How do you rate the crew?"),
    AIRCRAFT ("How do you rate the aircraft?"),
    SEAT ("How do you rate the seats?"),
    PEOPLE ("How crowded was the flight?");

    private String question;
    Parameter(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
