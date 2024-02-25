package org.app.bussiness.dao;

import org.app.domain.Menu;
import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;

import java.util.List;
import java.util.Set;

public interface SoupDAO {
    List<Soup> saveSoups(List<Soup> soup);

    Soup saveSoup(Soup soup);

    Set<Soup> findAvailableByMenu(Menu menu);

    void deleteSoup(Integer soupId);

    Soup findById(Integer soupId);

    Soup updateQuantitySoup(Integer soupId, Integer quantity);
}
