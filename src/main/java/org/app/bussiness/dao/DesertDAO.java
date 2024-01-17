package org.app.bussiness.dao;

import org.app.domain.Desert;
import org.app.infrastructure.database.entity.DesertEntity;

import java.util.List;

public interface DesertDAO {
    List<Desert> saveDeserts(List<Desert> desert);
}
