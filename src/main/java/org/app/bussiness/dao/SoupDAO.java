package org.app.bussiness.dao;

import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;

import java.util.List;

public interface SoupDAO {
    List<Soup> saveSoups(List<Soup> soup);
}
