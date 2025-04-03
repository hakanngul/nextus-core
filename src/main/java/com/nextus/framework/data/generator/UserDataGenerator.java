package com.nextus.framework.data.generator;

import com.nextus.framework.data.model.UserModel;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Generates fake UserModel data using PODAM.
 */
public class UserDataGenerator {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private UserDataGenerator() {}

    public static UserModel generateUser() {
        UserModel user = factory.manufacturePojo(UserModel.class);
        return UserModel.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .active(true)
                .address(user.getAddress())
                .company(user.getCompany())
                .build();
    }

    public static UserModel generateInactiveUser() {
        UserModel user = factory.manufacturePojo(UserModel.class);
        return UserModel.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .website(user.getWebsite())
                .active(false)
                .address(user.getAddress())
                .company(user.getCompany())
                .build();
    }
}