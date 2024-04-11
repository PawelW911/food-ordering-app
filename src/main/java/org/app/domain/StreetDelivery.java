package org.app.domain;

import lombok.*;

import java.util.Set;

@With
//@Value
@Data
@Builder
@EqualsAndHashCode
@ToString(of = {"streetDeliveryId", "street", "city", "postalCode", "restaurant"})
@AllArgsConstructor
@NoArgsConstructor
public class StreetDelivery {

    private Integer streetDeliveryId;
    private String street;
    private String city;
    private String postalCode;
    private Set<Restaurant> restaurant;
}
