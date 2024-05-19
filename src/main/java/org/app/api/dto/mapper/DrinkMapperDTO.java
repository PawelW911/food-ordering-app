package org.app.api.dto.mapper;

import org.app.api.dto.DrinkDTO;
import org.app.domain.Drink;
import org.app.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DrinkMapperDTO {

    default Drink mapFromDTO(DrinkDTO drinkDTO, int quantity, Menu menu) {
        if (drinkDTO.getAlcoholFree() == null) {
            drinkDTO.setAlcoholFree(false);
        }
        return Drink.builder()
                .name(drinkDTO.getName())
                .composition(drinkDTO.getComposition())
                .alcoholFree(drinkDTO.getAlcoholFree())
                .price(drinkDTO.getPrice())
                .quantity(quantity)
                .menu(menu)
                .build();
    }

    Drink mapFromDTO(DrinkDTO drinkDTO);
}
