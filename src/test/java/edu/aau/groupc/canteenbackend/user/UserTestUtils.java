package edu.aau.groupc.canteenbackend.user;

import java.util.Random;

public abstract class UserTestUtils {

    private static Random rand = new Random();
    public static String getRandomUsername() {
        return rand.ints(97, 122).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
