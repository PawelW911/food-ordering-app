package org.app.api.dto.mapper;

import org.app.api.dto.OwnerDTO;
import org.app.domain.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerDTOMapper {

    Owner mapFromDTO(OwnerDTO ownerDTO);
}
