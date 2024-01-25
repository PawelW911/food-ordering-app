package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SoupMapper {

    SoupEntity mapToEntity(Soup soup);

    @Mapping(target = "menu", ignore = true)
    Soup mapFromEntity(SoupEntity soupEntity);
}
