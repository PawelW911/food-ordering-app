package org.app.api.dto;

import lombok.*;
import org.app.domain.Address;

@Data
@Getter
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
