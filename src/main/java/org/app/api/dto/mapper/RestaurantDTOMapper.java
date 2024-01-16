package org.app.api.dto.mapper;

import org.app.api.dto.RestaurantDTO;
import org.app.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantDTOMapper {
    RestaurantDTO mapToDTO(Restaurant restaurant);
}
