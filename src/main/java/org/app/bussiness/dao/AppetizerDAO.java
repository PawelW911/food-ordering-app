package org.app.bussiness.dao;

import org.app.domain.Appetizer;

import java.util.List;

public interface AppetizerDAO {
    List<Appetizer> saveAppetizers(List<Appetizer> appetizer);
}
