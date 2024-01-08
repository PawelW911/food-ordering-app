package org.app.infrastructure.database.repository.jpa;

import org.app.infrastructure.database.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MenuJpaRepository extends JpaRepository<MenuEntity, Integer> {


//    void saveAndFlush(MenuEntity toSave, Set<Integer> setWithIdMainMeals, Set<Integer> setWithIdAppetizers, Set<Integer> setWithIdSoups, Set<Integer> setWithIdDeserts, Set<Integer> setWithIdDrinks);
}
