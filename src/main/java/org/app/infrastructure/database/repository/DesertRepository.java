package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.DesertDAO;
import org.app.domain.Desert;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.repository.jpa.DesertJpaRepository;
import org.app.infrastructure.database.repository.mapper.DesertMapper;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DesertRepository implements DesertDAO {

    private final DesertJpaRepository desertJpaRepository;
    private final DesertMapper desertMapper;
    private final MenuMapper menuMapper;

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

    @Override
    public Desert saveDesert(Desert desert) {
        DesertEntity toSave = desertMapper.mapToEntity(desert);
        DesertEntity saved = desertJpaRepository.saveAndFlush(toSave);
        return desertMapper.mapFromEntity(saved);
    }


    @Override
    public Set<Desert> findAvailableByMenu(Menu menu) {
        List<DesertEntity> desertEntities = desertJpaRepository.findByMenu(menuMapper.mapToEntity(menu));
        return desertEntities.stream()
                .map(desertMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteDesert(Integer desertId) {
        desertJpaRepository.deleteById(desertId);
    }

    @Override
    public Desert findById(Integer desertId) {
        return desertMapper.mapFromEntity(desertJpaRepository.findById(desertId).orElseThrow());
    }

    @Override
    public Desert updateQuantityDesert(Integer desertId, Integer quantity) {
        desertJpaRepository.updateQuantityDesert(desertId, quantity);
        DesertEntity desertEntity = desertJpaRepository.findById(desertId).orElseThrow();
        return desertMapper.mapFromEntity(desertEntity);
    }

}
