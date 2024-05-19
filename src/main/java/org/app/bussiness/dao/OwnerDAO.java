package org.app.bussiness.dao;

import org.app.domain.Owner;
import org.app.infrastructure.database.entity.OwnerEntity;

public interface OwnerDAO {
    Owner saveOwner(Owner owner);

    OwnerEntity findByEmailAndReturnEntity(String email);

    Owner findByEmail(String email);
}
