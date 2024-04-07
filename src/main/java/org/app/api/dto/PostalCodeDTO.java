package org.app.api.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostalCodeDTO {

    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Incorrect format postal code")
    private String postalCode;
}
