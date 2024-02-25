package org.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
