package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.RestaurantDAO;
import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;

    @Transactional
    public Restaurant saveNewRestaurant(Restaurant restaurant) {
        Restaurant restaurantSaved = restaurantDAO.saveRestaurant(restaurant);
        if (restaurantSaved == null) {
            log.error("The attempt to save the restaurant wasn't a success");
        } else {
            log.info("Restaurant save a success, uniqueCode: [{}]", restaurant.getUniqueCode());
        }
        return restaurantSaved;
    }

    public List<Restaurant> findAvailableRestaurantByStreetDelivery(StreetDelivery streetDelivery) {
        List<Restaurant> restaurants = restaurantDAO.findRestaurantByStreetDelivery(streetDelivery);
        log.info("Available restaurant with street: [{}], [{}] is [{}]",
                streetDelivery.getStreet(),
                streetDelivery.getCity(),
                restaurants.size()
        );
        return restaurants;
    }

    public List<Restaurant> findAvailableRestaurantByOwner(Owner owner) {
        List<Restaurant> restaurants = restaurantDAO.findAvailableRestaurantByOwner(owner);
        log.info("Available restaurant with owner with email: [{}] is [{}]",
                owner.getEmail(),
                restaurants.size()
        );
        return restaurants;
    }

    public Restaurant findByUniqueCode(String uniqueCode) {
        Restaurant restaurant = restaurantDAO.findByUniqueCode(uniqueCode);
        if (restaurant == null) {
            throw new NotFoundException("Restaurant with unique code: [%s] does not exist.".formatted(uniqueCode));
        } else {
            log.info("Restaurant with unique code: [{}] is found", uniqueCode);
        }
        return restaurant;
    }

    public List<Restaurant> findAvailableRestaurant() {
        List<Restaurant> availableRestaurant = restaurantDAO.findAvailableRestaurant();
        log.info("Available restaurant is: [{}]",
                availableRestaurant.size()
        );
        return availableRestaurant;
    }

}
