package org.app.infrastructure.zipCode;

import org.app.domain.StreetDelivery;
import org.openapitools.client.model.ZipCodeEntry;
import org.springframework.stereotype.Component;

@Component
public class ZipCodeMapper {

    StreetDelivery map(ZipCodeEntry zipCodeEntry) {
        return StreetDelivery.builder()
                .street(zipCodeEntry.getUlica())
                .city(zipCodeEntry.getMiejscowosc())
                .build();
    }
}
