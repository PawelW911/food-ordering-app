package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Appetizer;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppetizerMapper {
    AppetizerEntity mapToEntity(Appetizer appetizer);

    @Mapping(target = "menu", ignore = true)
    Appetizer mapFromEntity(AppetizerEntity appetizerEntity);
}
