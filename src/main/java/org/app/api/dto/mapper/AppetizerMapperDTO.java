package org.app.api.dto.mapper;

import org.app.api.dto.AppetizerDTO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppetizerMapperDTO {

    default Appetizer mapFromDTO(AppetizerDTO appetizerDTO, int quantity, Menu menu) {
        return Appetizer.builder()
                .name(appetizerDTO.getName())
                .composition(appetizerDTO.getComposition())
                .price(appetizerDTO.getPrice())
                .quantity(quantity)
                .menu(menu)
                .build();
    }
}
