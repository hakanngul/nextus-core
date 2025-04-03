package com.nextus.framework.utils;

import com.github.javafaker.Faker;
import java.util.Locale;

public final class RandomUtils {
    
    private static final Faker faker = new Faker(Locale.of("tr"));
    
    private RandomUtils() {
        // Utility class
    }

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getFullName() {
        return faker.name().fullName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String getPassword(int minLength, int maxLength) {
        return faker.internet().password(minLength, maxLength, true, true, true);
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String getZipCode() {
        return faker.address().zipCode();
    }

    public static String getCompanyName() {
        return faker.company().name();
    }

    public static String getJobTitle() {
        return faker.job().title();
    }

    public static String getCreditCardNumber() {
        return faker.business().creditCardNumber();
    }

    public static String getCreditCardExpiry() {
        return faker.business().creditCardExpiry();
    }

    public static String getUsername() {
        return faker.name().username();
    }

    public static String getDomainName() {
        return faker.internet().domainName();
    }

    public static String getUrl() {
        return faker.internet().url();
    }

    public static int getNumber(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String getRandomText(int maxLength) {
        return faker.lorem().characters(maxLength);
    }

    public static String getTCno() {
        StringBuilder tcno = new StringBuilder();
        tcno.append(faker.number().numberBetween(1, 9));
        for (int i = 0; i < 10; i++) {
            tcno.append(faker.number().numberBetween(0, 9));
        }
        return tcno.toString();
    }
}