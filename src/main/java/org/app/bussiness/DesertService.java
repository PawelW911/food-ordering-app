package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.domain.Menu;
import org.app.domain.Restaurant;
import org.app.domain.Soup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DesertService {

    private final DesertDAO desertDAO;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public List<Desert> saveNewDeserts(List<Desert> deserts) {
        return desertDAO.saveDeserts(deserts);
    }

    @Transactional
    public Desert saveNewDesert(Desert desert) {
        return desertDAO.saveDesert(desert);
    }


    public Set<Desert> findAvailable(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Menu menu = menuService.findByRestaurant(restaurant);
        return desertDAO.findAvailableByMenu(menu);
    }

    @Transactional
    public void deleteDesert(Integer desertId) {
        desertDAO.deleteDesert(desertId);
    }

    public Desert findById(Integer desertId) {
        return desertDAO.findById(desertId);
    }
}
