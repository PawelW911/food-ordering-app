package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.DrinkEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkJpaRepository extends JpaRepository<DrinkEntity, Integer> {

    List<DrinkEntity> findByMenu(MenuEntity menuEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DrinkEntity a SET a.quantity = :quantity WHERE a.drinkId = :drinkId")
    void updateQuantityDrink(
            @Param("drinkId") Integer drinkId,
            @Param("quantity") Integer quantity
    );
}
