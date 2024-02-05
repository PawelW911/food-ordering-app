package org.app.api.dto.mapper;

import org.app.api.dto.SoupDTO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Soup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SoupMapperDTO {
    default Soup mapFromDTO(SoupDTO soupDTO, int quantity, Menu menu){
        return Soup.builder()
                .name(soupDTO.getName())
                .composition(soupDTO.getComposition())
                .price(soupDTO.getPrice())
                .quantity(quantity)
                .menu(menu)
                .build();
    }

    Soup mapFromDTO(SoupDTO soupDTO);
}
