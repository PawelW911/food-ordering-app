package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Appetizer;
import org.app.domain.Desert;
import org.app.domain.Menu;
import org.app.domain.Soup;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.DesertJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DesertRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final DesertRepository desertRepository;
    private final DesertJpaRepository desertJpaRepository;

    private final OwnerMapper ownerMapper;
    private final OwnerJpaRepository ownerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private void saveDeserts() {
        desertRepository.saveDesert(DesertFixtures.someDesertsForPolishFood().stream()
                .map(desertr -> desertr.withQuantity(0))
                .toList().get(0));
        desertRepository.saveDesert(DesertFixtures.someDesertsForPolishFood().stream()
                .map(desertr -> desertr.withQuantity(0))
                .toList().get(1));

    }

    private Menu saveRestaurantAndOwnerAndMenu() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        Menu menu = menuRepository.saveMenu(MenuFixtures.someMenuPolishFood());
        desertRepository.saveDesert(DesertFixtures.someDesertsForPolishFood().stream()
                .map(desertr -> desertr.withQuantity(0).withMenu(menu))
                .toList().get(0));
        desertRepository.saveDesert(DesertFixtures.someDesertsForPolishFood().stream()
                .map(desertr -> desertr.withQuantity(0).withMenu(menu))
                .toList().get(1));

        return menu;
    }

    @Test
    void correctlySaveDesert() {
        // given
        List<Desert> deserts = DesertFixtures.someDesertsForPolishFood().stream()
                .map(desert -> desert.withQuantity(0))
                .toList();

        // when
        List<DesertEntity> allDesertBeforeSave = desertJpaRepository.findAll();
        desertRepository.saveDeserts(deserts);
        List<DesertEntity> allDesertAfterSave = desertJpaRepository.findAll();

        // then
        Assertions.assertThat(allDesertBeforeSave).hasSize(allDesertAfterSave.size() - deserts.size());

    }

    @Test
    void correctlyDesertSave() {
        // given
        Desert desert = DesertFixtures.someDesertsForPolishFood().stream()
                .map(desertr -> desertr.withQuantity(0))
                .toList().get(0);

        // when
        List<DesertEntity> allDesertsBeforeSave = desertJpaRepository.findAll();
        desertRepository.saveDesert(desert);
        List<DesertEntity> allDesertAfterSave = desertJpaRepository.findAll();

        // then
        Assertions.assertThat(allDesertsBeforeSave).hasSize(allDesertAfterSave.size() - 1);
    }

    @Test
    void correctlyFindAvailable() {
        Menu menu = saveRestaurantAndOwnerAndMenu();
        // given, when
        List<DesertEntity> allBefore = desertJpaRepository.findAll();
        Set<Desert> deserts = desertRepository.findAvailableByMenu(menu);

        // then
        Assertions.assertThat(deserts).hasSize(allBefore.size());
    }

    @Test
    void correctlyDesertDelete() {
        saveDeserts();
        // given
        Integer desertId = desertJpaRepository.findAll().get(0).getDesertId();

        // when
        List<DesertEntity> allDesertsBeforeDelete = desertJpaRepository.findAll();
        desertRepository.deleteDesert(desertId);
        List<DesertEntity> allDesertsAfterDelete = desertJpaRepository.findAll();

        //
        Assertions.assertThat(allDesertsBeforeDelete).hasSize(allDesertsAfterDelete.size() + 1);

    }

    @Test
    void correctlyFindByIdDesert() {
        saveDeserts();
        // given
        Integer desertId = desertJpaRepository.findAll().get(0).getDesertId();

        // when
        Desert desert = desertRepository.findById(desertId);

        // then
        Assertions.assertThat(desert.getDesertId()).isEqualTo(desertId);
    }

    @Transactional
    @Test
    void correctlyUpdateQuantityDesert() {
        saveDeserts();
        // given
        Integer desertId = desertJpaRepository.findAll().get(0).getDesertId();
        Integer quantity = 5;

        // when
        Desert desert = desertRepository.updateQuantityDesert(desertId, quantity);

        // then
        Integer quantityUpdate = desertJpaRepository.findById(desertId).orElseThrow().getQuantity();
        Assertions.assertThat(quantityUpdate)
                .isEqualTo(quantity);
    }
}
