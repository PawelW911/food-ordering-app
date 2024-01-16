package org.app.domain;

import lombok.*;

import java.util.Set;

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
    Set<Restaurant> restaurant;
}
