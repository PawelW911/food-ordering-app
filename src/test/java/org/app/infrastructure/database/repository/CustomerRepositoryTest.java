package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.domain.Customer;
import org.app.infrastructure.configuration.CleanDatabaseBeforeRepositoryTestAndConfiguration;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.util.CustomerFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerRepositoryTest extends CleanDatabaseBeforeRepositoryTestAndConfiguration {

    private final CustomerRepository customerRepository;
    private final CustomerJpaRepository customerJpaRepository;
    

    @Test
    void correctlySaveCustomer() {
        
        // given
        Customer customer = CustomerFixtures.someCustomer1();

        // when
        List<CustomerEntity> allCustomerBeforeSave = customerJpaRepository.findAll();
        customerRepository.saveCustomer(customer);
        List<CustomerEntity> allCustomerAfterSave = customerJpaRepository.findAll();

        // then
        Assertions.assertThat(allCustomerBeforeSave).hasSize(allCustomerAfterSave.size()-1);
    }

}
