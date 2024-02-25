package org.app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.app.security.RoleEntity;
import org.hibernate.validator.constraints.Length;

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
