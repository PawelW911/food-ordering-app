package org.app.business.dao;

import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;

public interface SoupDAO {
    Soup saveSoup(Soup soup);
    Integer saveSoupAndReturnId(SoupEntity soupEntity);
}
