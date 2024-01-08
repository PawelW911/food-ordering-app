package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.SoupDAO;
import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.SoupJpaRepository;
import org.app.infrastructure.database.repository.mapper.SoupMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class SoupRepository implements SoupDAO {

    private final SoupJpaRepository soupJpaRepository;
    private final SoupMapper soupMapper;

    @Override
    public Soup saveSoup(Soup soup) {
        SoupEntity toSave = soupMapper.mapToEntity(soup);
        SoupEntity saved = soupJpaRepository.saveAndFlush(toSave);
        return soupMapper.mapFromEntity(saved);
    }

    @Override
    public Integer saveSoupAndReturnId(SoupEntity soupEntity) {
        return soupJpaRepository.saveAndFlush(soupEntity).getSoupId();
    }

}
