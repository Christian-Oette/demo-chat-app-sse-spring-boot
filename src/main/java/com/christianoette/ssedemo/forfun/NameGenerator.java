package com.christianoette.ssedemo.forfun;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NameGenerator {
    private static final List<String> ANIMALS = List.of(
            "Lion", "Fox", "Turtle", "Dolphin", "Cat", "Snake", "Mouse", "Eagle",
            "Tiger","Bee","Kangaroo","Squirrel","Penguin","Shark");
    private static final AtomicInteger NUMBER = new AtomicInteger(10);

    public static String generateName() {
        var rand = new Random();
        var nextNumber = NameGenerator.NUMBER.getAndIncrement();
        var randomAnimal =  ANIMALS.get(rand.nextInt(ANIMALS.size()));
        return String.format("%s-%s", randomAnimal, nextNumber);
    }

}
