package org.praktikum.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class TestDataGenerator {
    private TestDataGenerator() {throw new AssertionError();}


    public static String randomEmail() {
        return "mail" + RandomStringUtils.randomNumeric(6) + "@yandex.ru";

    }

    public static String randomName() {
        String[] firstNames = {"John", "Emma", "Olivia", "Ava", "Isabella", "Sophia", "Robin"};
        String[] lastNames = {"Doe", "Smith", "Johnson", "Williams", "Jones", "Brown", "Hood"};
        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)] + " "
                + lastNames[random.nextInt(lastNames.length)];

    }
}
