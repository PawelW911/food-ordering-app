package org.app.util;

import lombok.experimental.UtilityClass;
import org.app.domain.Owner;

@UtilityClass
public class OwnerFixtures {

    public static Owner someOwner1() {
        return Owner.builder()
                .name("Tomek")
                .surname("Kowalski")
                .email("tomek1@gmail.com")
                .phone("+48 565 642 543")
                .user(UserFixtures.someUserForOwner())
                .build();
    }

    public static Owner someOwner2() {
        return Owner.builder()
                .name("Jan")
                .surname("Kowalski")
                .email("jan5@gmail.com")
                .phone("+48 432 765 322")
                .user(UserFixtures.someUserForOwner())
                .build();
    }

    public static Owner someOwner3() {
        return Owner.builder()
                .name("Wojciech")
                .surname("Wojciechowski")
                .email("woj75@gmail.com")
                .phone("+48 323 632 543")
                .build();
    }


}
