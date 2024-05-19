package org.app.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"opinionId", "text", "stars", "dateTime", "customer"})
public class Opinion {

    Integer opinionId;
    String text;
    Integer stars;
    OffsetDateTime dateTime;
    Customer customer;
    Restaurant restaurant;
}
