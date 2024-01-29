package org.app.domain;

import lombok.*;

import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"streetDeliveryId", "street", "city", "restaurant"})
public class StreetDelivery {

    Integer streetDeliveryId;
    String street;
    String city;
    String postalCode;
    Set<Restaurant> restaurant;
}
