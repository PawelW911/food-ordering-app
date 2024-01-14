package org.app.business.dao;

import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;

import java.util.List;

public interface SoupDAO {
    List<Soup> saveSoups(List<Soup> soup);
}
