package org.app.business.dao;

import org.app.domain.Address;
import org.app.infrastructure.database.entity.AddressEntity;

public interface AddressDAO {
    Address saveAddress(Address address);

    AddressEntity saveAddressAndReturnEntity(Address address);
}
