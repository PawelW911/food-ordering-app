package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.business.dao.AppetizerDAO;
import org.app.domain.Appetizer;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.infrastructure.database.repository.mapper.AppetizerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AppetizerRepository implements AppetizerDAO {

    private final AppetizerJpaRepository appetizerJpaRepository;
    private final AppetizerMapper appetizerMapper;

    @Override
    public List<Appetizer> saveAppetizers(List<Appetizer> appetizer) {
        List<AppetizerEntity> toSave = appetizer.stream()
                .map(appetizerMapper::mapToEntity)
                .toList();
        List<AppetizerEntity> saved = appetizerJpaRepository.saveAllAndFlush(toSave);
        return saved.stream()
                .map(appetizerMapper::mapFromEntity)
                .toList();
    }


}
