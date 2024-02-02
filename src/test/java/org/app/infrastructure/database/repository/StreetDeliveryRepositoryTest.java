package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.entity.StreetDeliveryEntity;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.jpa.StreetDeliveryJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.infrastructure.database.repository.mapper.RestaurantMapper;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.app.util.StreetDeliveryFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.app.util.StreetDeliveryFixtures.*;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StreetDeliveryRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final StreetDeliveryRepository streetDeliveryRepository;
    private final StreetDeliveryJpaRepository streetDeliveryJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;

    private void saveRestaurantAndOwner() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }

    private void saveRestaurantAndOwnerAndStreetDelivery() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        streetDeliveryRepository.saveStreetDeliveries(
                StreetDeliveryFixtures.someStreetDelivery1(),
                restaurantRepository.findByUniqueCode("WAW45")
        );
    }

    private void saveTwoRestaurantAndOwnerAndStreetDelivery() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
        streetDeliveryRepository.saveStreetDeliveries(
                StreetDeliveryFixtures.someStreetDelivery1(),
                restaurantRepository.findByUniqueCode("WAW45")
        );
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant2());
    }

    private void deleteAll() {
        streetDeliveryJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        ownerJpaRepository.deleteAll();
    }

    @Test
    void correctlySaveStreetDelivery() {
        saveRestaurantAndOwner();
        // given
        Set<StreetDelivery> streetDeliveries = someStreetDelivery1();

        // when
        streetDeliveryRepository.saveStreetDeliveries(streetDeliveries, restaurantRepository.findByUniqueCode("WAW45"));
        List<StreetDeliveryEntity> allStreet = streetDeliveryJpaRepository.findAll();

        // then
        Assertions.assertThat(allStreet).hasSize(streetDeliveries.size());

        deleteAll();
    }

    @Test
    void correctlySaveWhenExistingStreetDelivery() {
        saveTwoRestaurantAndOwnerAndStreetDelivery();
        // given
        Set<StreetDelivery> streetDeliveries = someStreetDelivery1();

        // when

        List<StreetDeliveryEntity> allStreetBeforeSave = streetDeliveryJpaRepository.findAll();
        streetDeliveryRepository.saveStreetDeliveries(streetDeliveries, restaurantRepository.findByUniqueCode("WAW60"));
        List<StreetDeliveryEntity> allStreetAfterSave = streetDeliveryJpaRepository.findAll();

        // then
        Assertions.assertThat(allStreetAfterSave).hasSize(allStreetBeforeSave.size());
        Assertions.assertThat(allStreetAfterSave.get(0).getRestaurants())
                .hasSize(allStreetBeforeSave.get(0).getRestaurants().size() + 1);

        deleteAll();
    }

    @Test
    void correctlyFindPostalCodeByRestaurant() {
        saveRestaurantAndOwnerAndStreetDelivery();
        // given
        Map<String, List<StreetDelivery>> mapByPostalCode =
                someStreetDelivery1().stream().collect(Collectors.groupingBy(StreetDelivery::getPostalCode));

        // when
        Set<StreetDelivery> streetDeliveries =
                streetDeliveryRepository.findPostalCodeByRestaurant(restaurantRepository.findByUniqueCode("WAW45"));

        // then
        Map<String, List<StreetDelivery>> mapByPostalCodeFind =
                streetDeliveries.stream().collect(Collectors.groupingBy(StreetDelivery::getPostalCode));

        Assertions.assertThat(mapByPostalCodeFind).hasSize(mapByPostalCode.size());

        deleteAll();
    }

    @Test
    void correctlyFindStreetDeliveryByStreetAndCity() {
        saveTwoRestaurantAndOwnerAndStreetDelivery();
        // given
        List<StreetDelivery> streetDeliveryList = someStreetDelivery1().stream().toList();
        String street = streetDeliveryList.get(0).getStreet();
        String city = streetDeliveryList.get(0).getCity();
        String postalCode = streetDeliveryList.get(0).getPostalCode();

        // when
        StreetDelivery streetDelivery = streetDeliveryRepository.findByStreetAndCity(street, city);

        // then
        Assertions.assertThat(streetDelivery.getStreet()).isEqualTo(street);
        Assertions.assertThat(streetDelivery.getCity()).isEqualTo(city);
        Assertions.assertThat(streetDelivery.getPostalCode()).isEqualTo(postalCode);
    }
}
