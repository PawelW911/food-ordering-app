package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.RestaurantDAO;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final StreetDeliveryService streetDeliveryService;

    @Transactional
    public Restaurant saveNewRestaurant(Restaurant restaurant) {
        return restaurantDAO.saveRestaurant(restaurant);
    }

    public List<Restaurant> findAvailableRestaurantByStreetDelivery(String street, String city) {
        return restaurantDAO.findRestaurantByStreetDelivery(streetDeliveryService.findByStreetAndCity(street, city));
    }

    public List<Restaurant> findAvailableRestaurantByOwner(Owner owner) {
        return restaurantDAO.findAvailableRestaurantByOwner(owner);
    }

    public Restaurant findByUniqueCode(String uniqueCode) {
        return restaurantDAO.findByUniqueCode(uniqueCode);
    }
}
