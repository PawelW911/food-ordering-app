package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Menu;
import org.app.domain.Soup;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.jpa.SoupJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.MenuFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.app.util.SoupFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SoupRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final SoupRepository soupRepository;
    private final SoupJpaRepository soupJpaRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerJpaRepository ownerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private void saveSoups() {
        soupRepository.saveSoup(SoupFixtures.someSoupsForPolishFood().stream()
                .map(soupr -> soupr.withQuantity(0))
                .toList().get(0));
        soupRepository.saveSoup(SoupFixtures.someSoupsForPolishFood().stream()
                .map(soupr -> soupr.withQuantity(0))
                .toList().get(1));

    }

    private Menu saveRestaurantAndOwnerAndMenu() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        Menu menu = menuRepository.saveMenu(MenuFixtures.someMenuPolishFood());
        soupRepository.saveSoup(SoupFixtures.someSoupsForPolishFood().stream()
                .map(soupr -> soupr.withQuantity(0).withMenu(menu))
                .toList().get(0));
        soupRepository.saveSoup(SoupFixtures.someSoupsForPolishFood().stream()
                .map(soupr -> soupr.withQuantity(0).withMenu(menu))
                .toList().get(1));

        return menu;
    }

    @Test
    void correctlySaveSoups() {
        // given
        List<Soup> soups = SoupFixtures.someSoupsForPolishFood().stream()
                .map(soup -> soup.withQuantity(0))
                .toList();

        // when
        List<SoupEntity> allSoupBeforeSave = soupJpaRepository.findAll();
        soupRepository.saveSoups(soups);
        List<SoupEntity> allSoupAfterSave = soupJpaRepository.findAll();

        // then
        Assertions.assertThat(allSoupBeforeSave).hasSize(allSoupAfterSave.size() - soups.size());
    }

    @Test
    void correctlySoupSave() {
        // given
        Soup soup = SoupFixtures.someSoupsForPolishFood().stream()
                .map(soupr -> soupr.withQuantity(0))
                .toList().get(0);

        // when
        List<SoupEntity> allSoupsBeforeSave = soupJpaRepository.findAll();
        soupRepository.saveSoup(soup);
        List<SoupEntity> allSoupAfterSave = soupJpaRepository.findAll();

        // then
        Assertions.assertThat(allSoupsBeforeSave).hasSize(allSoupAfterSave.size() - 1);
    }

    @Test
    void correctlyFindAvailable() {
        Menu menu = saveRestaurantAndOwnerAndMenu();
        // given, when
        List<SoupEntity> allBefore = soupJpaRepository.findAll();
        Set<Soup> soups = soupRepository.findAvailableByMenu(menu);

        // then
        Assertions.assertThat(soups).hasSize(allBefore.size());
    }

    @Test
    void correctlySoupDelete() {
        saveSoups();
        // given
        Integer soupId = soupJpaRepository.findAll().get(0).getSoupId();

        // when
        List<SoupEntity> allSoupsBeforeDelete = soupJpaRepository.findAll();
        soupRepository.deleteSoup(soupId);
        List<SoupEntity> allSoupsAfterDelete = soupJpaRepository.findAll();

        //
        Assertions.assertThat(allSoupsBeforeDelete).hasSize(allSoupsAfterDelete.size() + 1);

    }

    @Test
    void correctlyFindByIdSoup() {
        saveSoups();
        // given
        Integer soupId = soupJpaRepository.findAll().get(0).getSoupId();

        // when
        Soup soup = soupRepository.findById(soupId);

        // then
        Assertions.assertThat(soup.getSoupId()).isEqualTo(soupId);
    }

    @Transactional
    @Test
    void correctlyUpdateQuantitySoup() {
        saveSoups();
        // given
        Integer soupId = soupJpaRepository.findAll().get(0).getSoupId();
        Integer quantity = 5;

        // when
        soupRepository.updateQuantitySoup(soupId, quantity);

        // then
        Integer quantityUpdate = soupJpaRepository.findById(soupId).orElseThrow().getQuantity();
        Assertions.assertThat(quantityUpdate)
                .isEqualTo(quantity);
    }
}
