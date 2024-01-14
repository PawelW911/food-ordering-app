package org.app.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"ownerId", "name", "surname", "email", "phone", "restaurant"})
public class Owner {

    Integer ownerId;
    String name;
    String surname;
    String email;
    String phone;
    Restaurant restaurant;
}
