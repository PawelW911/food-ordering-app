package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AppetizerService {

    private final AppetizerDAO appetizerDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public Appetizer saveNewAppetizer(Appetizer appetizer) {
        return appetizerDAO.saveAppetizer(appetizer);
    }

    @Transactional
    public List<Appetizer> saveNewAppetizers(List<Appetizer> appetizers) {
        return appetizerDAO.saveAppetizers(appetizers);
    }

    public Set<Appetizer> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        return appetizerDAO.findAvailableByMenu(menu);
    }

    @Transactional
    public void deleteAppetizer(Integer appetizerId) {
        appetizerDAO.deleteAppetizer(appetizerId);
    }
}
