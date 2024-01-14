package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Soup;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.SoupEntity;
import org.app.infrastructure.database.repository.jpa.SoupJpaRepository;
import org.app.util.SoupFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SoupRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final SoupRepository soupRepository;
    private final SoupJpaRepository soupJpaRepository;
    

    @Test
    void correctlySaveSoup() {
        // given
        List<Soup> soups = SoupFixtures.someSoupsForPolishFood().stream()
                .map(soup -> soup.withQuantity(0))
                .toList();

        // when
        List<SoupEntity> allSoupBeforeSave = soupJpaRepository.findAll();
        soupRepository.saveSoups(soups);
        List<SoupEntity> allSoupAfterSave = soupJpaRepository.findAll();

        // then
        Assertions.assertThat(allSoupBeforeSave).hasSize(allSoupAfterSave.size() - soups.size());
    }
}
