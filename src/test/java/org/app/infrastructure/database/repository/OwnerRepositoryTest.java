package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Owner;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.util.OwnerFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerRepository ownerRepository;
    

    @Test
    void correctlySaveOwner() {
        
        // given
        Owner owner = OwnerFixtures.someOwner1();

        // when
        ownerRepository.saveOwner(owner);
        OwnerEntity ownerEntity = ownerJpaRepository.findByEmail(owner.getEmail());

        // then
        Assertions.assertThatObject(ownerEntity).isNotNull();
    }

}
