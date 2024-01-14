package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Opinion;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.entity.OpinionEntity;
import org.app.infrastructure.database.entity.OwnerEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.jpa.OpinionJpaRepository;
import org.app.infrastructure.database.repository.jpa.OwnerJpaRepository;
import org.app.infrastructure.database.repository.mapper.CustomerMapper;
import org.app.infrastructure.database.repository.mapper.OwnerMapper;
import org.app.util.CustomerFixtures;
import org.app.util.OpinionFixtures;
import org.app.util.OwnerFixtures;
import org.app.util.RestaurantFixtures;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OpinionRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final OpinionRepository opinionRepository;
    private final OpinionJpaRepository opinionJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;
    private final RestaurantRepository restaurantRepository;
    private final OwnerJpaRepository ownerJpaRepository;
    private final CustomerMapper customerMapper;
    private final OwnerMapper ownerMapper;

    private void saveRestaurantAndCustomer() {
        OwnerEntity ownerEntity = ownerMapper.mapToEntity(OwnerFixtures.someOwner1());
        ownerJpaRepository.saveAndFlush(ownerEntity);
        CustomerEntity customerEntity = customerMapper
                .mapToEntity(CustomerFixtures.someCustomer1());
        customerJpaRepository.saveAndFlush(customerEntity);
        restaurantRepository.saveRestaurant(RestaurantFixtures.someRestaurant1());
    }

    @Test
    void correctlySaveOpinion() {
        saveRestaurantAndCustomer();
        // given
        Opinion opinion = OpinionFixtures.someOpinion1();

        // when
        List<OpinionEntity> allOpinionsBeforeSave = opinionJpaRepository.findAll();
        opinionRepository.saveOpinion(opinion);
        List<OpinionEntity> allOpinionsAfterSave = opinionJpaRepository.findAll();

        // then
        Assertions.assertThat(allOpinionsBeforeSave).hasSize(allOpinionsAfterSave.size()-1);
    }
}
