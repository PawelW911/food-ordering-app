package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {

    default MenuEntity mapToEntity(Menu menu, RestaurantEntity restaurantEntity) {
        return MenuEntity.builder()
                .name(menu.getName())
                .description(menu.getDescription())
                .restaurant(restaurantEntity)
                .build();
    }

    default Menu mapFromEntity(MenuEntity saved, Restaurant restaurant) {
        return Menu.builder()
                .menuId(saved.getMenuId())
                .name(saved.getName())
                .description(saved.getDescription())
                .restaurant(restaurant)
                .build();
    }

    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "appetizers", ignore = true)
    @Mapping(target = "soups", ignore = true)
    @Mapping(target = "mainMeals", ignore = true)
    @Mapping(target = "deserts", ignore = true)
    @Mapping(target = "drinks", ignore = true)
    Menu mapFromEntity(MenuEntity menuEntity);

    MenuEntity mapToEntity(Menu menu);
}
