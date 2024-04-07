package org.app.api.dto.mapper;

import org.app.api.dto.MenuDTO;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapperDTO {
    default Menu mapFromDTO(MenuDTO menuDTO, Restaurant restaurant) {
        if (!(menuDTO.getMenuId()==null)) {
            return Menu.builder()
                    .menuId(restaurant.getMenu().getMenuId())
                    .name(menuDTO.getMenuName())
                    .description(menuDTO.getMenuDescription())
                    .restaurant(restaurant)
                    .build();
        } else {
            return Menu.builder()
                    .name(menuDTO.getMenuName())
                    .description(menuDTO.getMenuDescription())
                    .restaurant(restaurant)
                    .build();
        }
    }

    default MenuDTO mapToDto(Menu menu) {
        return MenuDTO.builder()
                .menuName(menu.getName())
                .menuDescription(menu.getDescription())
                .build();
    }
}
