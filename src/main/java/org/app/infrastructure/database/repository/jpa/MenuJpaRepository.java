package org.app.infrastructure.database.repository.jpa;

import org.app.domain.Restaurant;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MenuJpaRepository extends JpaRepository<MenuEntity, Integer> {

    MenuEntity findByRestaurant(RestaurantEntity restaurantEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE MenuEntity m SET m.name = :name, m.description = :description WHERE m.menuId = :menuId")
    void updateMenu(
            @Param("menuId") Integer menuId,
            @Param("name") String name,
            @Param("description") String description
    );

}
