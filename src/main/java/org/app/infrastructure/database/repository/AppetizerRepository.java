package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.entity.MenuEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.mapper.AppetizerMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AppetizerRepository implements AppetizerDAO {

    private final AppetizerJpaRepository appetizerJpaRepository;
    private final AppetizerMapper appetizerMapper;

    @Override
    public Appetizer saveAppetizer(Appetizer appetizer) {
        AppetizerEntity toSave = appetizerMapper.mapToEntity(appetizer);
        AppetizerEntity saved = appetizerJpaRepository.saveAndFlush(toSave);
        return appetizerMapper.mapFromEntity(saved);
    }

    @Override
    public Integer saveAppetizerAndReturnId(AppetizerEntity appetizerEntity) {
        return appetizerJpaRepository.saveAndFlush(appetizerEntity).getAppetizerId();
    }

}
