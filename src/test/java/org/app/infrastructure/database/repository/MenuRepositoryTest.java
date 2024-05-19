package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.MenuJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.app.util.MenuFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MenuRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final MenuRepository menuRepository;
    private final MenuJpaRepository menuJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;
    private final RestaurantMapper restaurantMapper;

    private void saveRestaurantAndOwner() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }
    private Integer saveMenu() {
        return menuRepository.saveMenu(MenuFixtures.someMenuPolishFood()).getMenuId();
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

    @Test
    void correctlyFindMenuById() {
        saveRestaurantAndOwner();
        // given
        Integer menuId = saveMenu();
        Restaurant restaurant = restaurantRepository
                .findByUniqueCode(RestaurantFixtures.someRestaurant1().getUniqueCode());
        // when
        Menu menu = menuRepository.findByRestaurant(restaurant);

        // then
        Assertions.assertThat(menuId).isEqualTo(menu.getMenuId());

    }

    @Transactional
    @Test
    void correctlyUpdateMenu() {
        saveRestaurantAndOwner();
        saveMenu();
        // given
        MenuEntity menuBeforeUpdate = menuJpaRepository
                .findByRestaurant(restaurantMapper.mapToEntity((restaurantRepository
                        .findByUniqueCode(RestaurantFixtures.someRestaurant1().getUniqueCode()))));
        Menu menuToUpdate = MenuFixtures
                .someMenuPolishFoodUpdate()
                .withRestaurant(restaurantRepository
                        .findByUniqueCode(RestaurantFixtures.someRestaurant1().getUniqueCode()));

        // when
        Menu menuAfterUpdate = menuRepository.updateMenu(menuToUpdate);

        // then
        Assertions.assertThat(menuBeforeUpdate.getMenuId()).isEqualTo(menuAfterUpdate.getMenuId());
        Assertions.assertThat(menuToUpdate.getName()).isEqualTo(menuAfterUpdate.getName());
        Assertions.assertThat(menuToUpdate.getDescription()).isEqualTo(menuAfterUpdate.getDescription());
    }
}
