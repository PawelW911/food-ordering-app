package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.domain.exception.NotFoundException;
import org.app.infrastructure.zipCode.ZipCodeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class StreetDeliveryService {

    private final StreetDeliveryDAO streetDeliveryDAO;
    private final RestaurantService restaurantService;
    private final ZipCodeDAO zipCodeDAO;

    @Transactional
    public Set<StreetDelivery> saveNewStreetsDelivery(String postalCode, String uniqueCode) {
        HashSet<StreetDelivery> streetDeliveries = new HashSet<>(zipCodeDAO.getStreetDeliveryByZipCode(postalCode));
        Set<StreetDelivery> streetDeliveriesSaved = streetDeliveryDAO.saveStreetDeliveries(
                streetDeliveries,
                restaurantService.findByUniqueCode(uniqueCode));
        if (!streetDeliveriesSaved.isEmpty()) {
            log.info("Save [{}] new street delivery", streetDeliveriesSaved.size());
        }
        return streetDeliveriesSaved;
    }

    public Set<StreetDelivery> postalCodeByRestaurantUniqueCode(String uniqueCode) {
        Restaurant restaurant = restaurantService.findByUniqueCode(uniqueCode);
        Set<StreetDelivery> streetDeliveries =
                streetDeliveryDAO.findPostalCodeByRestaurant(restaurant);
        log.info("Available street delivery is [{}] for restaurant [{}]", streetDeliveries.size(), restaurant);
        return streetDeliveries;
    }

    public StreetDelivery findByStreetAndCity(String street, String city) {
        Optional<StreetDelivery> streetDelivery = streetDeliveryDAO.findByStreetAndCity(street, city);
        if (streetDelivery.isEmpty()) {
            throw new NotFoundException("Not found with street [%s], and city [%s]".formatted(street, city));
        }
        return streetDelivery.get();
    }

    public Set<StreetDelivery> findAvailableStreetsDelivery() {
        Set<StreetDelivery> availableStreetsDelivery = streetDeliveryDAO.findAvailableStreetsDelivery();
        log.info("Available street delivery is [{}]", availableStreetsDelivery.size());
        return availableStreetsDelivery;
    }
}
