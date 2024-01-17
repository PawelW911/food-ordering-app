package org.app.bussiness;

import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.domain.StreetDelivery;
import org.app.util.StreetDeliveryFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class StreetDeliveryServiceTest {

    @Mock
    private StreetDeliveryDAO streetDeliveryDAO;

    @InjectMocks
    private StreetDeliveryService streetDeliveryService;

    @Test
    void checkSaveNewStreetDelivery() {
        // given
        Set<StreetDelivery> streetDeliveriesExample = StreetDeliveryFixtures.someStreetDelivery1();

        Mockito.when(streetDeliveryDAO.saveStreetDeliveries(Mockito.anySet())).thenReturn(streetDeliveriesExample);

        // when
        Set<StreetDelivery> streetDeliveries = streetDeliveryService.saveNewStreetsDelivery(streetDeliveriesExample);

        //
        Assertions.assertEquals(streetDeliveriesExample.size(), streetDeliveries.size());
    }
}
