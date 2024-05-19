package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.MainMealEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainMealJpaRepository extends JpaRepository<MainMealEntity, Integer> {

    List<MainMealEntity> findByMenu(MenuEntity menuEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MainMealEntity a SET a.quantity = :quantity WHERE a.mainMealId = :mainMealId")
    void updateQuantityMainMeal(
            @Param("mainMealId") Integer mainMealId,
            @Param("quantity") Integer quantity
    );
}
