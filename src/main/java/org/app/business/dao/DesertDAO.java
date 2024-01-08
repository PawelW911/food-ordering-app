package org.app.business.dao;

import org.app.domain.Desert;
import org.app.infrastructure.database.entity.DesertEntity;

public interface DesertDAO {
    Desert saveDesert(Desert desert);
    Integer saveDesertAndReturnId(DesertEntity desertEntity);
}
