package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppetizerJpaRepository extends JpaRepository<AppetizerEntity, Integer> {

    List<AppetizerEntity> findByMenu(MenuEntity menuEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AppetizerEntity a SET a.quantity = :quantity WHERE a.appetizerId = :appetizerId")
    void updateQuantityAppetizer(
            @Param("appetizerId") Integer appetizerId,
            @Param("quantity") Integer quantity
    );

}
