package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.StreetDelivery;
import org.app.infrastructure.zipCode.ZipCodeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StreetDeliveryService {

    private final StreetDeliveryDAO streetDeliveryDAO;
    private final RestaurantService restaurantService;
    private final ZipCodeDAO zipCodeDAO;

    @Transactional
    public Set<StreetDelivery> saveNewStreetsDelivery(String postalCode, String uniqueCode) {
        HashSet<StreetDelivery> streetDeliveries = new HashSet<>(zipCodeDAO.getStreetDeliveryByZipCode(postalCode));
//        System.out.println(streetDeliveries);
        return streetDeliveryDAO.saveStreetDeliveries(
                streetDeliveries,
                restaurantService.findByUniqueCode(uniqueCode));
    }

    public Set<StreetDelivery> postalCodeByRestaurantUniqueCode(String uniqueCode) {
        return streetDeliveryDAO.findPostalCodeByRestaurant(restaurantService.findByUniqueCode(uniqueCode));
    }

    public StreetDelivery findByStreetAndCity(String street, String city) {
        return null;
    }
}
