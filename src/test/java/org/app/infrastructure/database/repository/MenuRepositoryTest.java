package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Menu;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.*;
import org.app.infrastructure.database.repository.mapper.CustomerMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MenuRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final MenuRepository menuRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;

    private void saveRestaurantAndOwner() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }

    @Test
    void correctlySaveMenu() {
        
        saveRestaurantAndOwner();
        // given
        Menu menu = MenuFixtures.someMenuPolishFood();

        // when
        List<MenuEntity> allMenuBeforeSave = menuJpaRepository.findAll();
        menuRepository.saveMenu(menu);
        List<MenuEntity> allMenuAfterSave = menuJpaRepository.findAll();

        // then
        Assertions.assertThat(allMenuBeforeSave).hasSize(allMenuAfterSave.size()-1);
    }
}
