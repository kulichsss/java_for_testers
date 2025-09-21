package ru.stqa.mantis.common;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonFunction {
    public static String randomString(int n) {
        var rnd = new Random();
        Supplier<Integer> randomNumber = () -> rnd.nextInt(26);
        String result = Stream.generate(randomNumber)
                .limit(n)
                .map(i -> 'a' + i)
                .map(Character::toString)
                .collect(Collectors.joining());
        return result;
    }
}
