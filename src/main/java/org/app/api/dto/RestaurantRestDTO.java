package org.app.api.dto;

import lombok.*;
import org.app.domain.Address;
import org.app.domain.Menu;
import org.app.domain.StreetDelivery;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRestDTO {

    private String uniqueCode;
    private String name;
    private String typeFood;
    private String email;
    private String phone;
    private String openingHours;
    private Address address;
}
