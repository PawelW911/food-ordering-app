package org.app.bussiness.dao;

import org.app.domain.Address;
import org.app.infrastructure.database.entity.AddressEntity;

public interface AddressDAO {
    Address saveAddress(Address address);

    AddressEntity saveAddressAndReturnEntity(Address address);
}
