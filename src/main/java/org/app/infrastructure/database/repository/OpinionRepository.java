package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.OpinionDAO;
import org.app.domain.Opinion;
import org.app.infrastructure.database.entity.OpinionEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.OpinionJpaRepository;
import org.app.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import org.app.infrastructure.database.repository.mapper.*;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OpinionRepository implements OpinionDAO {

    OpinionJpaRepository opinionJpaRepository;
    CustomerJpaRepository customerJpaRepository;
    RestaurantJpaRepository restaurantJpaRepository;
    CustomerMapper customerMapper;
    OpinionMapper opinionMapper;
    RestaurantMapper restaurantMapper;
    AddressMapper addressMapper;
    OwnerMapper ownerMapper;

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
}
