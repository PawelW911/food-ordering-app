package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.app.infrastructure.database.entity.OpinionEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.OpinionJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class OpinionRepository implements OpinionDAO {

    private final OpinionJpaRepository opinionJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final CustomerMapper customerMapper;
    private final OpinionMapper opinionMapper;
    private final RestaurantMapper restaurantMapper;
    private final AddressMapper addressMapper;
    private final OwnerMapper ownerMapper;

    @Override
    public Opinion saveOpinion(Opinion opinion) {
        OpinionEntity toSave = opinionMapper
                .mapToEntity(
                        opinion,
                        customerJpaRepository.findByEmail(opinion.getCustomer().getEmail()),
                        restaurantJpaRepository.findByUniqueCode(opinion.getRestaurant().getUniqueCode())
                );
        OpinionEntity saved = opinionJpaRepository.saveAndFlush(toSave);
        return opinionMapper.mapFromEntity(saved, customerMapper, restaurantMapper, addressMapper, ownerMapper);
    }

    @Override
    public List<Opinion> findByRestaurant(String restaurantUniqueCode) {
        List<OpinionEntity> opinionEntities =
                opinionJpaRepository.findByRestaurant(restaurantJpaRepository.findByUniqueCode(restaurantUniqueCode));
        return opinionEntities.stream()
                .map(opinionMapper::mapFromEntity)
                .toList();
    }
}
