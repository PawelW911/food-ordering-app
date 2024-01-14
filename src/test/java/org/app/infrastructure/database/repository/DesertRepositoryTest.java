package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Desert;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.DesertEntity;
import org.app.infrastructure.database.repository.jpa.DesertJpaRepository;
import org.app.util.DesertFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DesertRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final DesertRepository desertRepository;
    private final DesertJpaRepository desertJpaRepository;
    

    @Test
    void correctlySaveDesert() {
        // given
        List<Desert> deserts = DesertFixtures.someDesertsForPolishFood().stream()
                .map(desert -> desert.withQuantity(0))
                .toList();

        // when
        List<DesertEntity> allDesertBeforeSave = desertJpaRepository.findAll();
        desertRepository.saveDeserts(deserts);
        List<DesertEntity> allDesertAfterSave = desertJpaRepository.findAll();

        // then
        Assertions.assertThat(allDesertBeforeSave).hasSize(allDesertAfterSave.size() - deserts.size());

    }
}
