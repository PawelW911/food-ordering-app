package org.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.domain.StreetDelivery;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableStreetsDeliveryDTO {

    List<StreetDeliveryDTO> streetsDelivery;
}