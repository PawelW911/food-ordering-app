package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Menu;
import org.app.infrastructure.database.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {
//    MenuEntity mapToEntity(Menu menu);
//
//    Menu mapFromEntity(MenuEntity menuEntity);

    default MenuEntity mapToEntity(
            Menu menu,
            MainMealMapper mainMealMapper,
            AppetizerMapper appetizerMapper,
            SoupMapper soupMapper,
            DesertMapper desertMapper,
            DrinkMapper drinkMapper) {
        return MenuEntity.builder()
                .mainMeals(menu.getMainMeals().stream().map(mainMealMapper::mapToEntity).collect(Collectors.toSet()))
                .appetizers(menu.getAppetizers().stream().map(appetizerMapper::mapToEntity).collect(Collectors.toSet()))
                .soups(menu.getSoups().stream().map(soupMapper::mapToEntity).collect(Collectors.toSet()))
                .deserts(menu.getDeserts().stream().map(desertMapper::mapToEntity).collect(Collectors.toSet()))
                .drinks(menu.getDrinks().stream().map(drinkMapper::mapToEntity).collect(Collectors.toSet()))
                .build();
    }

    Menu mapFromEntity(MenuEntity menuEntity);
}
