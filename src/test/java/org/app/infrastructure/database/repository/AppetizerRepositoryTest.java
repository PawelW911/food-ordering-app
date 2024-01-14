package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Appetizer;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.AppetizerEntity;
import org.app.infrastructure.database.repository.jpa.AppetizerJpaRepository;
import org.app.util.AppetizerFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AppetizerRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final AppetizerRepository appetizerRepository;
    private final AppetizerJpaRepository appetizerJpaRepository;


    @Test
    void correctlyAppetizersSave() {
        // given
        List<Appetizer> appetizers = AppetizerFixtures.someAppetizersForPolishFood().stream()
                .map(appetizer -> appetizer.withQuantity(0))
                .toList();

        // when
        List<AppetizerEntity> allAppetizersBeforeSave = appetizerJpaRepository.findAll();
        appetizerRepository.saveAppetizers(appetizers);
        List<AppetizerEntity> allAppetizerAfterSave = appetizerJpaRepository.findAll();

        // then
        Assertions.assertThat(allAppetizersBeforeSave).hasSize(allAppetizerAfterSave.size() - appetizers.size());
    }
}
