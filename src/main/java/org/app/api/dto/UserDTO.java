package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    boolean userType;
    String name;
    String surname;
    String email;
    String phone;
    String userName;
    String password;
}
