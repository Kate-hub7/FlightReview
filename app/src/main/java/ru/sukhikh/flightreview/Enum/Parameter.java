package ru.sukhikh.flightreview.Enum;

public enum Parameter{
    FOOD ("How do you rate the food?", "Food"),
    FLIGHT ("", ""),
    CREW ("How do you rate the crew?", "Crew"),
    AIRCRAFT ("How do you rate the aircraft?", "Aircraft"),
    SEAT ("How do you rate the seats?", "Seats"),
    PEOPLE ("How crowded was the flight?", "Flight experience");

    private final String question;
    private final String name;

    Parameter(String question, String name) {

        this.question = question;
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public String getName(){ return name;}
}
