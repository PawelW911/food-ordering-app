package org.app.bussiness;

import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.util.RestaurantFixtures;
import org.app.util.StreetDeliveryFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.app.util.StreetDeliveryFixtures.someStreetDelivery1;

@ExtendWith(MockitoExtension.class)
public class StreetDeliveryServiceTest {

    @Mock
    private StreetDeliveryDAO streetDeliveryDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private ZipCodeDAO zipCodeDAO;

    @InjectMocks
    private StreetDeliveryService streetDeliveryService;

    @Test
    void checkSaveNewStreetDelivery() {
        // given
        Set<StreetDelivery> streetDeliveriesExample = StreetDeliveryFixtures.someStreetDelivery1();
        String uniqueCode = "WAW45";
        String postalCode = "00-212";

        Mockito.when(zipCodeDAO.getStreetDeliveryByZipCode(Mockito.anyString()))
                .thenReturn(streetDeliveriesExample.stream().toList());
        Mockito.when(streetDeliveryDAO.saveStreetDeliveries(Mockito.anySet(), Mockito.any(Restaurant.class)))
                .thenReturn(streetDeliveriesExample);
        Mockito.when(restaurantService.findByUniqueCode(uniqueCode))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        // when
        Set<StreetDelivery> streetDeliveries = streetDeliveryService.saveNewStreetsDelivery(postalCode, uniqueCode);

        //
        Assertions.assertEquals(streetDeliveriesExample.size(), streetDeliveries.size());
    }

    @Test
    void checkCorrectlyFindPostalCodeByRestaurant() {
        // given
        Set<StreetDelivery> streetDeliveriesExample = StreetDeliveryFixtures.someStreetDelivery2();
        String uniqueCode = "WAW45";

        Mockito.when(restaurantService.findByUniqueCode(uniqueCode))
                .thenReturn(RestaurantFixtures.someRestaurant1());
        Mockito.when(streetDeliveryDAO.findPostalCodeByRestaurant(RestaurantFixtures.someRestaurant1()))
                .thenReturn(streetDeliveriesExample);
        // when
        Set<StreetDelivery> streetDeliveries = streetDeliveryService.postalCodeByRestaurantUniqueCode(uniqueCode);

        // then
        Assertions.assertEquals(streetDeliveriesExample.size(), streetDeliveries.size());
    }

    @Test
    void checkCorrectlyFindStreetDeliveryByStreetAndCity() {
        // given
        List<StreetDelivery> streetDeliveryList = someStreetDelivery1().stream().toList();
        String street = streetDeliveryList.get(0).getStreet();
        String city = streetDeliveryList.get(0).getCity();

        Mockito.when(streetDeliveryDAO.findByStreetAndCity(street, city))
                .thenReturn(Optional.of(streetDeliveryList.get(0)));
        // when
        StreetDelivery streetDelivery = streetDeliveryService.findByStreetAndCity(street, city);

        // then
        Assertions.assertEquals(StreetDelivery.class, streetDelivery.getClass());
    }

    @Test
    void checkCorrectlyFindAvailableStreetsDelivery() {
        // given
        Set<StreetDelivery> streetDeliveries = someStreetDelivery1();

        Mockito.when(streetDeliveryDAO.findAvailableStreetsDelivery()).thenReturn(streetDeliveries);

        // when
        Set<StreetDelivery> streetDeliverySet = streetDeliveryService.findAvailableStreetsDelivery();

        // then
        Assertions.assertEquals(streetDeliveries.size(), streetDeliverySet.size());
    }
}
