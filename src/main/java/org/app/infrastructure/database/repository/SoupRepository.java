package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.SoupDAO;
import org.app.domain.Menu;
import org.app.domain.Soup;
import org.app.domain.exception.NotFoundException;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.SoupJpaRepository;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.app.infrastructure.database.repository.mapper.SoupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SoupRepository implements SoupDAO {

    private final SoupJpaRepository soupJpaRepository;
    private final SoupMapper soupMapper;
    private final MenuMapper menuMapper;

    @Override
    public List<Soup> saveSoups(List<Soup> soups) {
        List<SoupEntity> toSave = soups.stream()
                .map(soupMapper::mapToEntity)
                .toList();
        List<SoupEntity> saved = soupJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(soupMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Soup saveSoup(Soup soup) {
        SoupEntity toSave = soupMapper.mapToEntity(soup);
        SoupEntity saved = soupJpaRepository.saveAndFlush(toSave);
        return soupMapper.mapFromEntity(saved);
    }


    @Override
    public Set<Soup> findAvailableByMenu(Menu menu) {
        List<SoupEntity> soupEntities = soupJpaRepository.findByMenu(menuMapper.mapToEntity(menu));
        return soupEntities.stream()
                .map(soupMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteSoup(Integer soupId) {
        soupJpaRepository.deleteById(soupId);
    }

    @Override
    public Soup findById(Integer soupId) {
        return soupMapper.mapFromEntity(soupJpaRepository.findById(soupId).orElseThrow(
                () -> new NotFoundException("Not found soup entity with id: [%s]".formatted(soupId))
        ));
    }

    @Override
    public Soup updateQuantitySoup(Integer soupId, Integer quantity) {
        soupJpaRepository.updateQuantitySoup(soupId, quantity);
        SoupEntity soupEntity = soupJpaRepository.findById(soupId).orElseThrow();
        return soupMapper.mapFromEntity(soupEntity);
    }
}
