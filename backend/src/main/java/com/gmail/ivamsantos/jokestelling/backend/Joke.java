package com.gmail.ivamsantos.jokestelling.backend;

/**
 * The object model for the data we are sending through endpoints
 */
public class Joke {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}