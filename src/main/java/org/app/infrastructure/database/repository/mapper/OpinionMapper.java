package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Opinion;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.entity.OpinionEntity;
import org.app.infrastructure.database.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OpinionMapper {
    default OpinionEntity mapToEntity(Opinion opinion, CustomerEntity customerEntity, RestaurantEntity restaurantEntity) {
        return OpinionEntity.builder()
                .text(opinion.getText())
                .stars(opinion.getStars())
                .dateTime(opinion.getDateTime())
                .customer(customerEntity)
                .restaurant(restaurantEntity)
                .build();
    }

    default Opinion mapFromEntity(OpinionEntity opinionEntity, CustomerMapper customerMapper, RestaurantMapper restaurantMapper, AddressMapper addressMapper, OwnerMapper ownerMapper) {
        return Opinion.builder()
                .opinionId(opinionEntity.getOpinionId())
                .text(opinionEntity.getText())
                .stars(opinionEntity.getStars())
                .dateTime(opinionEntity.getDateTime())
                .customer(customerMapper.mapFromEntity(opinionEntity.getCustomer()))
                .restaurant(restaurantMapper.mapFromEntity(opinionEntity.getRestaurant(), addressMapper, ownerMapper))
                .build();
    }
}
