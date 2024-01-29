package org.app.infrastructure.zipCode;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.ZipCodeDAO;
import org.app.domain.StreetDelivery;
import org.openapitools.client.api.DefaultApi;
import org.openapitools.client.model.ZipCodeEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class ZipCodeImpl implements ZipCodeDAO {

    private DefaultApi defaultApi;
    private ZipCodeMapper zipCodeMapper;

    public List<StreetDelivery> getStreetDeliveryByZipCode(String zipCode) {
        Mono<ResponseEntity<List<ZipCodeEntry>>> responseEntityMono =
                defaultApi.zipCodeInfoWithHttpInfo(zipCode, "application/json", UUID.randomUUID().toString());

        List<ZipCodeEntry> zipCodeEntryList = new ArrayList<>();
        responseEntityMono
                .flatMap(responseEntity -> {
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        return Mono.just(Objects.requireNonNull(responseEntity.getBody()));
                    } else {
                        return Mono.error(new RuntimeException("Error fetching zip code info: " + responseEntity.getStatusCode()));
                    }
                })
                .subscribe(zipCodeEntryList::addAll, Throwable::printStackTrace);
        return zipCodeEntryList.stream()
                .map(zipCodeEntry -> zipCodeMapper.map(zipCodeEntry))
                .toList();
    }
}
