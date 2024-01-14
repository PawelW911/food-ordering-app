package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Owner;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {

    OwnerEntity mapToEntity(Owner owner);

    Owner mapFromEntity(OwnerEntity owner);
}
