package org.app.api.dto.mapper;

import org.app.api.dto.StreetDeliveryDTO;
import org.app.domain.StreetDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StreetDeliveryMapperDTO {
    StreetDeliveryDTO mapToDTO(StreetDelivery streetDelivery);
}
