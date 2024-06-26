package org.app.infrastructure.zipCode;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.StreetDelivery;
import org.app.domain.exception.NotFoundException;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.ZipCodeEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ZipCodeImpl implements ZipCodeDAO {

    private final DefaultApi defaultApi;
    private final ZipCodeMapper zipCodeMapper;

    @Override
    public List<StreetDelivery> getStreetDeliveryByZipCode(String zipCode) {
        Mono<ResponseEntity<List<ZipCodeEntry>>> responseEntityMono =
                defaultApi.zipCodeInfoWithHttpInfo(zipCode, "application/json", UUID.randomUUID().toString());

        List<ZipCodeEntry> zipCodeEntryList =
        responseEntityMono
                .flatMap(responseEntity -> {
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(Objects.requireNonNull(responseEntity.getBody()));
                    } else {
                        return Mono.error(new RuntimeException("Error fetching zip code info: " + responseEntity.getStatusCode()));
                    }
                })
                .block();
        if (zipCodeEntryList != null) {
            return zipCodeEntryList.stream()
                    .map(zipCodeMapper::map)
                    .toList();
        } else {
            throw new NotFoundException(String.format("Not found by zip code: %s", zipCode));
        }
    }
}
