package org.qamation.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {
    public static int getRandomInteger(int min, int max) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(min,max+1);
    }

    public static String getRandomNumber(int min, int max) {
        int random = getRandomInteger(min, max);
        return String.valueOf(random);
    }
}
