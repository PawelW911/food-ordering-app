package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.repository.jpa.DesertJpaRepository;
import org.app.infrastructure.database.repository.mapper.DesertMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DesertRepository implements DesertDAO {

    private final DesertJpaRepository desertJpaRepository;
    private final DesertMapper desertMapper;

    @Override
    public Desert saveDesert(Desert desert) {
        DesertEntity toSave = desertMapper.mapToEntity(desert);
        DesertEntity saved = desertJpaRepository.saveAndFlush(toSave);
        return desertMapper.mapFromEntity(saved);
    }

    @Override
    public Integer saveDesertAndReturnId(DesertEntity desertEntity) {
        return desertJpaRepository.saveAndFlush(desertEntity).getDesertId();
    }

}
