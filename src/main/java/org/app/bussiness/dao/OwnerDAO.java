package org.app.bussiness.dao;

import org.app.domain.Owner;
import org.app.infrastructure.database.entity.OwnerEntity;

public interface OwnerDAO {
    Owner saveOwner(Owner owner);

    OwnerEntity saveOwnerAndReturnEntity(Owner owner);

    OwnerEntity findByEmail(String email);
}
