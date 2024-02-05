package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.FoodOrder;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.FoodOrderJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.CustomerMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.CustomerFixtures;
import org.app.util.FoodOrderFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FoodOrderRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final FoodOrderJpaRepository foodOrderJpaRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final CustomerMapper customerMapper;
    private final OwnerMapper ownerMapper;
    private final AppetizerJpaRepository appetizerJpaRepository;


    private void saveRestaurantAndCustomer() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        CustomerEntity customerEntity = customerMapper
                .mapToEntity(CustomerFixtures.someCustomer1());
        customerJpaRepository.saveAndFlush(customerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }

    @Test
    void correctlySaveFoodOrder() {
        saveRestaurantAndCustomer();
        // given
        FoodOrder foodOrder = FoodOrderFixtures.someFoodOrder2();

        // when
        List<FoodOrderEntity> allFoodOrdersBeforeSave = foodOrderJpaRepository.findAll();
        FoodOrder foodOrder1 = foodOrderRepository.saveFoodOrder(foodOrder);
        List<FoodOrderEntity> allFoodOrdersAfterSave = foodOrderJpaRepository.findAll();

        // then
        System.out.println("byJPA: " + appetizerJpaRepository.findAll());
        System.out.println(foodOrder1.getAppetizers());
        System.out.println(foodOrder1.getSoups());
        System.out.println(foodOrder1.getMainMeals());
        System.out.println(foodOrder1.getDeserts());
        System.out.println(foodOrder1.getDrinks());

        Assertions.assertThat(allFoodOrdersBeforeSave).hasSize(allFoodOrdersAfterSave.size() - 1);
    }
}
