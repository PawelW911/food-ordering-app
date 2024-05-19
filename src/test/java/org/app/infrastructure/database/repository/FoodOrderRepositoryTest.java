package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.ForFoodOrderChoose;
import org.app.domain.Customer;
import org.app.domain.FoodOrder;
import org.app.domain.Restaurant;
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
import org.app.security.RoleRepository;
import org.app.util.CustomerFixtures;
import org.app.util.FoodOrderFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FoodOrderRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final FoodOrderJpaRepository foodOrderJpaRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final CustomerMapper customerMapper;
    private final OwnerMapper ownerMapper;
    private final AppetizerJpaRepository appetizerJpaRepository;
    private final RoleRepository roleRepository;


    private void saveRestaurantAndCustomer() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        CustomerEntity customerEntity = customerMapper
                .mapToEntity(CustomerFixtures.someCustomer1());
        customerJpaRepository.saveAndFlush(customerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }

    private void saveFoodOrder() {
        foodOrderRepository.saveFoodOrder(FoodOrderFixtures.someFoodOrder2());
    }

    @Test
    void correctlySaveFoodOrder() {
        saveRestaurantAndCustomer();
        // given
        FoodOrder foodOrder = FoodOrderFixtures.someFoodOrder2();

        // when
        List<FoodOrderEntity> allFoodOrdersBeforeSave = foodOrderJpaRepository.findAll();
        foodOrderRepository.saveFoodOrder(foodOrder);
        List<FoodOrderEntity> allFoodOrdersAfterSave = foodOrderJpaRepository.findAll();

        // then
        Assertions.assertThat(allFoodOrdersBeforeSave).hasSize(allFoodOrdersAfterSave.size() - 1);
    }

    @Test
    void correctlyFindFoodOrdersByCustomer() {
        saveRestaurantAndCustomer();
        saveFoodOrder();
        // given
        Customer customer = customerRepository.findByEmail(CustomerFixtures.someCustomer1().getEmail());

        // when
        Set<FoodOrder> foodOrderSet = foodOrderRepository.findByCustomer(customer);

        // then
        Assertions.assertThat(foodOrderSet).hasSize(1);
        Assertions.assertThat(foodOrderSet.stream().toList().get(0).getCustomer().getEmail())
                .isEqualTo(customer.getEmail());
    }

    @Test
    void correctlyFindFoodOrderByFoodOrderNumber() {
        saveRestaurantAndCustomer();
        saveFoodOrder();
        // given
        String foodOrderNumber = FoodOrderFixtures.someFoodOrder2().getFoodOrderNumber();

        // when

        FoodOrder foodOrder = foodOrderRepository.findByFoodOrderNumber(
                foodOrderNumber,
                ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString()
        );

        // then
        Assertions.assertThat(foodOrder).isNotNull();
        Assertions.assertThat(foodOrder.getFoodOrderNumber()).isEqualTo(foodOrderNumber);
    }

    @Transactional
    @Test
    void correctlyUpdateSumCost() {
        saveRestaurantAndCustomer();
        saveFoodOrder();
        // given
        BigDecimal newSumCost = new BigDecimal("49.39");

        // when
        foodOrderRepository.updateSumCost(newSumCost, FoodOrderFixtures.someFoodOrder2().getFoodOrderNumber());

        // then
        Assertions.assertThat(foodOrderRepository.findByFoodOrderNumber(
                                FoodOrderFixtures.someFoodOrder2().getFoodOrderNumber(),
                                ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString())
                        .getSumCost())
                .isEqualTo(newSumCost);
    }

    @Test
    void correctlyFindAvailableByRestaurant() {
        saveRestaurantAndCustomer();
        saveFoodOrder();
        // given
        Restaurant restaurant = restaurantRepository.findByUniqueCode(
                RestaurantFixtures.someRestaurant1().getUniqueCode());

        // when
        Set<FoodOrder> foodOrders = foodOrderRepository.findAvailableByRestaurant(restaurant);

        // then
        Assertions.assertThat(foodOrders).hasSize(1);
    }

    @Transactional
    @Test
    void correctlyUpdateCompletedDateTime() {
        saveRestaurantAndCustomer();
        saveFoodOrder();
        // given
        OffsetDateTime completedDateTime = OffsetDateTime.of(2023, 5, 10, 20, 14, 36, 0, ZoneOffset.UTC);
        String foodOrderNumber = FoodOrderFixtures.someFoodOrder2().getFoodOrderNumber();

        // when
        foodOrderRepository.updateCompletedDateTime(foodOrderNumber, completedDateTime);

        // then
        Assertions.assertThat(foodOrderRepository.findByFoodOrderNumber(
                        foodOrderNumber,
                        ForFoodOrderChoose.MAP_WITHOUT_SET_DISHES.toString()
                ).getCompletedDateTime())
                .isEqualTo(completedDateTime);

    }
}
