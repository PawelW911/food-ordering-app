package org.app.infrastructure.database.repository.jpa;

import org.app.domain.FoodOrder;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Repository
public interface FoodOrderJpaRepository extends JpaRepository<FoodOrderEntity, Integer> {


    FoodOrderEntity findByFoodOrderNumber(String orderNumber);

    Set<FoodOrderEntity> findByCustomer(CustomerEntity mapToEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FoodOrderEntity f SET f.sumCost = :newSumCost WHERE f.foodOrderNumber = :foodOrderNumber")
    void updateSumCost(
            @Param("newSumCost") BigDecimal newSumCost,
            @Param("foodOrderNumber") String foodOrderNumber
    );

    Set<FoodOrderEntity> findByRestaurant(RestaurantEntity restaurantEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FoodOrderEntity f SET f.completedDateTime = " +
            ":completedDateTime WHERE f.foodOrderNumber = :foodOrderNumber")
    void updateCompletedDateTime(
            @Param("completedDateTime") OffsetDateTime completedDateTime,
            @Param("foodOrderNumber") String foodOrderNumber
            );
}
