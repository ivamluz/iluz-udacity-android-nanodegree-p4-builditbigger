package com.gmail.ivamsantos;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JokesProvider {
    private static List<String> jokes = Arrays.asList(
            "Funny joke 1",
            "Funny joke 2",
            "Funny joke 3",
            "Funny joke 4",
            "Funny joke 5",
            "Funny joke 6",
            "Funny joke 7",
            "Funny joke 8",
            "Funny joke 9",
            "Funny joke 10"
    );
    private Random randomGenerator;

    public JokesProvider() {
        randomGenerator = new Random();
    }

    public String getJoke() {
        int randomIndex = randomGenerator.nextInt(jokes.size());

        return jokes.get(randomIndex);
    }
}
