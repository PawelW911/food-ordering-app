package org.app.bussiness.dao;

import org.app.domain.Desert;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.DesertEntity;

import java.util.List;
import java.util.Set;

public interface DesertDAO {
    List<Desert> saveDeserts(List<Desert> desert);

    Desert saveDesert(Desert desert);

    Set<Desert> findAvailableByMenu(Menu menu);

    void deleteDesert(Integer desertId);

    Desert findById(Integer desertId);

    Desert updateQuantityDesert(Integer desertId, Integer quantity);
}
