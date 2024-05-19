package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.entity.SoupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoupJpaRepository extends JpaRepository<SoupEntity, Integer> {

    List<SoupEntity> findByMenu(MenuEntity menuEntity);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE SoupEntity a SET a.quantity = :quantity WHERE a.soupId = :soupId")
    void updateQuantitySoup(
            @Param("soupId") Integer soupId,
            @Param("quantity") Integer quantity
    );
}
