package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.OwnerDAO;
import org.app.domain.Owner;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class OwnerRepository implements OwnerDAO {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Owner saveOwner(Owner owner) {
        OwnerEntity toSave = ownerMapper.mapToEntity(owner);
        toSave.getUser().setPassword(passwordEncoder.encode(toSave.getUser().getPassword()));
        OwnerEntity saved = ownerJpaRepository.saveAndFlush(toSave);
        return ownerMapper.mapFromEntity(saved);
    }

    @Override
    public OwnerEntity findByEmailAndReturnEntity(String email) {
        return ownerJpaRepository.findByEmail(email);
    }

    @Override
    public Owner findByEmail(String email) {
        return ownerMapper.mapFromEntity(ownerJpaRepository.findByEmail(email));
    }
}
