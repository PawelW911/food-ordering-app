package org.app.bussiness.dao;

import org.app.domain.Owner;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;

import java.util.List;

public interface RestaurantDAO {
    Restaurant saveRestaurant(Restaurant restaurant);

    List<Restaurant> findRestaurantByStreetDelivery(StreetDelivery streetDelivery);

    List<Restaurant> findAvailableRestaurantByOwner(Owner ownerExample);
}
