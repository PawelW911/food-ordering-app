package org.app.api.dto.mapper;

import org.app.api.dto.MainMealDTO;
import org.app.domain.Appetizer;
import org.app.domain.MainMeal;
import org.app.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MainMealMapperDTO {
    default MainMeal mapFromDTO(MainMealDTO mainMealDTO, int quantity, Menu menu) {
        return MainMeal.builder()
                .name(mainMealDTO.getName())
                .composition(mainMealDTO.getComposition())
                .price(mainMealDTO.getPrice())
                .quantity(quantity)
                .menu(menu)
                .build();
    }

    MainMeal mapFromDTO(MainMealDTO mainMealDTO);

}
