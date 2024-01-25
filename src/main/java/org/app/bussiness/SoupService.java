package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.SoupDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.Soup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SoupService {

    private final SoupDAO soupDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Soup> saveNewSoups(List<Soup> soups) {
        return soupDAO.saveSoups(soups);
    }

    @Transactional
    public Soup saveNewSoup(Soup soup) {
        return soupDAO.saveSoup(soup);
    }


    public Set<Soup> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        return soupDAO.findAvailableByMenu(menu);
    }

    @Transactional
    public void deleteSoup(Integer soupId) {
        soupDAO.deleteSoup(soupId);
    }
}
