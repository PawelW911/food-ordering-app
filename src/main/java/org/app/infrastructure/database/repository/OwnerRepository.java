package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.OwnerDAO;
import org.app.domain.Owner;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerMapper.mapToEntity(owner);
        OwnerEntity saved = ownerJpaRepository.saveAndFlush(toSave);
        return ownerMapper.mapFromEntity(saved);
    }

    @Override
    public OwnerEntity saveOwnerAndReturnEntity(Owner owner) {
        OwnerEntity toSave = ownerMapper.mapToEntity(owner);
        return ownerJpaRepository.saveAndFlush(toSave);
    }

    @Override
    public OwnerEntity findByEmail(String email) {
        return ownerJpaRepository.findByEmail(email);
    }
}
