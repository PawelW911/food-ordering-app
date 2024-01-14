package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.SoupDAO;
import org.app.domain.Soup;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.SoupJpaRepository;
import org.app.infrastructure.database.repository.mapper.SoupMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SoupRepository implements SoupDAO {

    private final SoupJpaRepository soupJpaRepository;
    private final SoupMapper soupMapper;

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

}
