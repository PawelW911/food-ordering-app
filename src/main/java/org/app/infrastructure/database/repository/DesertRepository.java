package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.repository.jpa.DesertJpaRepository;
import org.app.infrastructure.database.repository.mapper.DesertMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DesertRepository implements DesertDAO {

    private final DesertJpaRepository desertJpaRepository;
    private final DesertMapper desertMapper;

    @Override
    public List<Desert> saveDeserts(List<Desert> deserts) {
        List<DesertEntity> toSave = deserts.stream()
                .map(desertMapper::mapToEntity)
                .toList();
        List<DesertEntity> saved = desertJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(desertMapper::mapFromEntity)
                .toList();
    }


}
