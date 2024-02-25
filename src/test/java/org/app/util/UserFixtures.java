package org.app.util;

import org.app.domain.User;

public class UserFixtures {

    public static User someUserForOwner() {
        return User.builder()
                .userName("tomek_owner")
                .email("tomek1@gmail.com")
                .password("password")
                .active(true)
                .roles(RolesFixtures.someRolesForOwner())
                .build();

    }

    public static User someUserForCustomer() {
        return User.builder()
                .userName("stefan_customer")
                .email("stefan@gmail.com")
                .password("password")
                .active(true)
                .roles(RolesFixtures.someRolesForCustomer())
                .build();

    }


}
