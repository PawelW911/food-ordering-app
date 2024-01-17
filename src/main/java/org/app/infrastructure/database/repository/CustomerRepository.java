package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.CustomerDAO;
import org.app.domain.Customer;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    CustomerJpaRepository customerJpaRepository;
    CustomerMapper customerMapper;

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity toSave = customerMapper.mapToEntity(customer);
        CustomerEntity saved = customerJpaRepository.saveAndFlush(toSave);
        return customerMapper.mapFromEntity(saved);
    }
}
