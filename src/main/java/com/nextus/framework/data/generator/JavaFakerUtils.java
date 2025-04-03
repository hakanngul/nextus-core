package com.nextus.framework.data.generator;

import com.github.javafaker.Faker;
import java.util.Locale;

/**
 * JavaFakerUtils provides realistic random test data.
 */
public final class JavaFakerUtils {

    private static final Faker faker = new Faker(Locale.of("tr"));

    private JavaFakerUtils() {}

    public static String randomUsername() {
        return faker.name().username();
    }

    public static String randomUrl() {
        return faker.internet().url();
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }
}
