package org.app.api.dto.mapper;

import org.app.api.dto.DesertDTO;
import org.app.domain.Desert;
import org.app.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DesertMapperDTO {
    default Desert mapFromDTO(DesertDTO desertDTO, int quantity, Menu menu) {
        return Desert.builder()
                .name(desertDTO.getName())
                .composition(desertDTO.getComposition())
                .price(desertDTO.getPrice())
                .quantity(quantity)
                .menu(menu)
                .build();
    }

    Desert mapFromDTO(DesertDTO desertDTO);
}
