package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.app.domain.Restaurant;

import java.util.Set;

@Data
@AllArgsConstructor
public class OwnerDTO {

    String name;
    String surname;
    String email;
    String phone;
    Set<Restaurant> restaurant;
}
