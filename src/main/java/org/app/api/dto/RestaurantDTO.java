package org.app.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.domain.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private String uniqueCode;
    private String restaurantName;
    private String restaurantTypeFood;
    @Email
    private String restaurantEmail;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String restaurantPhone;
    @Pattern(
            regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]",
            message = "Incorrect format opening hours restaurant"
    )
    private String restaurantOpeningHours;
    private String restaurantAddressStreet;
    private String restaurantAddressLocalNumber;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Incorrect format postal code")
    private String restaurantAddressPostalCode;
    private String restaurantAddressCity;
    private Menu menu;
    private Set<StreetDelivery> streetDelivery;
    private Address address;
    private Set<Opinion> opinions;
    private Set<FoodOrder> orders;
    private Owner owner;
}
