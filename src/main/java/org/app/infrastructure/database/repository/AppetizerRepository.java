package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.domain.Menu;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.mapper.AppetizerMapper;
import org.app.infrastructure.database.repository.mapper.FoodOrderMapper;
import org.app.infrastructure.database.repository.mapper.MenuMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AppetizerRepository implements AppetizerDAO {

    private final AppetizerJpaRepository appetizerJpaRepository;
    private final AppetizerMapper appetizerMapper;
    private final MenuMapper menuMapper;
    private final FoodOrderMapper foodOrderMapper;

    @Override
    public Appetizer saveAppetizer(Appetizer appetizer) {
        AppetizerEntity toSave = appetizerMapper.mapToEntity(appetizer);
        AppetizerEntity saved = appetizerJpaRepository.saveAndFlush(toSave);
        return appetizerMapper.mapFromEntity(saved);
    }

    @Override
    public List<Appetizer> saveAppetizers(List<Appetizer> appetizer) {
        List<AppetizerEntity> toSave = appetizer.stream()
                .map(appetizer1 -> appetizerMapper.mapToEntity(
                        appetizer1,
                        foodOrderMapper.mapToEntity(appetizer1.getFoodOrder())
                ))
                .toList();
        List<AppetizerEntity> saved = appetizerJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(appetizerMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Set<Appetizer> findAvailableByMenu(Menu menu) {
        List<AppetizerEntity> appetizerEntities = appetizerJpaRepository.findByMenu(menuMapper.mapToEntity(menu));
        return appetizerEntities.stream()
                .map(appetizerMapper::mapFromEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteAppetizer(Integer appetizerId) {
        appetizerJpaRepository.deleteById(appetizerId);
    }

    @Override
    public Appetizer findById(Integer appetizerId) {
        return appetizerMapper.mapFromEntity(appetizerJpaRepository.findById(appetizerId).orElseThrow());
    }

    @Override
    public Appetizer updateQuantityAppetizer(Integer appetizerId, Integer quantity) {
        appetizerJpaRepository.updateQuantityAppetizer(appetizerId, quantity);
        AppetizerEntity appetizerEntity = appetizerJpaRepository.findById(appetizerId).orElseThrow();
        return appetizerMapper.mapFromEntity(appetizerEntity);
    }
}
