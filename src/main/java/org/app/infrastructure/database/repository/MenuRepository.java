package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.MenuDAO;
import org.app.domain.*;
import org.app.infrastructure.database.entity.AddressEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.MenuJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
}
