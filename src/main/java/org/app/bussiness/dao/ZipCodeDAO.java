package org.app.bussiness.dao;

import org.app.domain.StreetDelivery;

import java.util.List;

public interface ZipCodeDAO {
    List<StreetDelivery> getStreetDeliveryByZipCode(String zipCode);
}
