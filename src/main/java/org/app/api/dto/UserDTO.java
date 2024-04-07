package org.app.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String surname;
    @Email
    private String email;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String phone;
    private String userName;
    private String password;
    private Boolean active;
    private String street;
    private String localNumber;
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Incorrect format postal code")
    private String postalCode;
    private String city;
}
