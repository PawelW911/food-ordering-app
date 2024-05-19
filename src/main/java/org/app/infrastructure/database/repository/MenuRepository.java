package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.MenuDAO;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.app.infrastructure.database.repository.jpa.MenuJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.AddressMapper;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MenuRepository implements MenuDAO {

    private final MenuJpaRepository menuJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final MenuMapper menuMapper;
    private final RestaurantMapper restaurantMapper;
    private final AddressMapper addressMapper;
    private final OwnerMapper ownerMapper;


    @Override
    public Menu saveMenu(Menu menu) {
        MenuEntity toSave = menuMapper.mapToEntity(
                menu,
                restaurantJpaRepository.findByUniqueCode(menu.getRestaurant().getUniqueCode()));
        MenuEntity saved = menuJpaRepository.saveAndFlush(toSave);
        return menuMapper.mapFromEntity(
                saved,
                restaurantMapper.mapFromEntity(
                        saved.getRestaurant(),
                        addressMapper,
                        ownerMapper));
    }

    @Override
    public Menu findByRestaurant(Restaurant restaurant) {
        RestaurantEntity toFind = restaurantMapper.mapToEntity(restaurant);
        return menuMapper.mapFromEntity(menuJpaRepository.findByRestaurant(toFind));

    }

    @Override
    public Menu updateMenu(Menu menuToUpdate) {
        Menu menu = findByRestaurant(menuToUpdate.getRestaurant());
        menuJpaRepository.updateMenu(menu.getMenuId(), menuToUpdate.getName(), menuToUpdate.getDescription());
        MenuEntity afterUpdate = menuJpaRepository.findById(menu.getMenuId()).orElseThrow();
        return menuMapper.mapFromEntity(afterUpdate);
    }
}
