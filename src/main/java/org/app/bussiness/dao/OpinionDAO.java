package org.app.bussiness.dao;

import org.app.domain.Opinion;

import java.util.List;

public interface OpinionDAO {
    Opinion saveOpinion(Opinion opinion);

    List<Opinion> findByRestaurant(String restaurantUniqueCode);
}
