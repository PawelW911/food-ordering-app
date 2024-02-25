package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Appetizer;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.FoodOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppetizerMapper {
    AppetizerEntity mapToEntity(Appetizer appetizer);

    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "foodOrder", ignore = true)
    Appetizer mapFromEntity(AppetizerEntity appetizerEntity);

    default AppetizerEntity mapToEntity(Appetizer appetizer, FoodOrderEntity foodOrderEntity) {
        return AppetizerEntity.builder()
                .name(appetizer.getName())
                .composition(appetizer.getComposition())
                .price(appetizer.getPrice())
                .quantity(appetizer.getQuantity())
                .foodOrder(foodOrderEntity)
                .build();
    }
}
