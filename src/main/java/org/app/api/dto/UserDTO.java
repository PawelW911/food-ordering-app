package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    String name;
    String surname;
    String email;
    String phone;
    String userName;
    String password;
    Boolean active;
    String street;
    String localNumber;
    String postalCode;
    String city;
}
