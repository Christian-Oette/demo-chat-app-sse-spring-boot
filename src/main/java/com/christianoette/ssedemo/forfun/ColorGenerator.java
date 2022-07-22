package com.christianoette.ssedemo.forfun;

import java.util.List;
import java.util.Random;

public class ColorGenerator {
    private static final List<String> COLORS =
            List.of("f4a460",
                    "bdb76b",
                    "bc8f8f",
                    "ff1493",
                    "c71585",
                    "4b0082",
                    "0000cd",
                    "2f4f4f",
                    "ffa500",
                    "daa520",
                    "778899");

    public static String generateColor() {
        var rand = new Random();
        return COLORS.get(rand.nextInt(COLORS.size()));
    }

}
