package org.app.business.dao;

import org.app.domain.Appetizer;
import org.app.infrastructure.database.entity.AppetizerEntity;

public interface AppetizerDAO {
    Appetizer saveAppetizer(Appetizer appetizer);
    Integer saveAppetizerAndReturnId(AppetizerEntity appetizerEntity);
}
