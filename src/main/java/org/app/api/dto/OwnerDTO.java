package org.app.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.app.domain.Restaurant;

import java.util.Set;

@Data
@AllArgsConstructor
public class OwnerDTO {

    private String name;
    private String surname;
    @Email
    private String email;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private Set<Restaurant> restaurant;
}
