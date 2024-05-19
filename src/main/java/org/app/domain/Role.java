package org.app.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString
public class Role {

    int id;
    String role;
}
