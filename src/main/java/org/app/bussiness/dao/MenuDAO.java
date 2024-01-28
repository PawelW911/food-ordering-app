package org.app.bussiness.dao;

import org.app.domain.Menu;
import org.app.domain.Restaurant;

public interface MenuDAO {
    Menu saveMenu(Menu menu);

    Menu findByRestaurant(Restaurant restaurant);

    Menu updateMenu(Menu menuToUpdate);
}
