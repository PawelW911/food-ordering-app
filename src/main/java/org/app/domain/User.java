package org.app.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString
public class User {

    int id;
    String userName;
    String email;
    String password;
    Boolean active;
    Set<Role> roles;
}
