package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.StreetDeliveryDAO;
import org.app.domain.StreetDelivery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class StreetDeliveryService {

    private final StreetDeliveryDAO streetDeliveryDAO;

    @Transactional
    public Set<StreetDelivery> saveNewStreetsDelivery(Set<StreetDelivery> streetDeliveries) {
        return streetDeliveryDAO.saveStreetDeliveries(streetDeliveries);
    }
}
