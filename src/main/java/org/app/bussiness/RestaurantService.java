package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.RestaurantDAO;
import org.app.domain.Restaurant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;

    @Transactional
    public Restaurant saveNewRestaurant(Restaurant restaurant) {
        return restaurantDAO.saveRestaurant(restaurant);
    }
}
