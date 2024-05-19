package org.app.bussiness.dao;

import org.app.domain.Appetizer;
import org.app.domain.Menu;

import java.util.List;
import java.util.Set;

public interface AppetizerDAO {
    List<Appetizer> saveAppetizers(List<Appetizer> appetizer);

    Appetizer saveAppetizer(Appetizer appetizer);

    Set<Appetizer> findAvailableByMenu(Menu menu);

    void deleteAppetizer(Integer appetizerId);

    Appetizer findById(Integer appetizerId);

    Appetizer updateQuantityAppetizer(Integer appetizerId, Integer quantity);
}
