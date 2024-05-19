package org.app.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.app.bussiness.dao.CustomerDAO;
import org.app.domain.Customer;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.app.infrastructure.database.repository.jpa.CustomerJpaRepository;
import org.app.infrastructure.database.repository.mapper.CustomerMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity toSave = customerMapper.mapToEntity(customer);
        toSave.getUser().setPassword(passwordEncoder.encode(toSave.getUser().getPassword()));
        CustomerEntity saved = customerJpaRepository.saveAndFlush(toSave);
        return customerMapper.mapFromEntity(saved);
    }

    @Override
    public Customer findByEmail(String email) {
        CustomerEntity customerFind = customerJpaRepository.findByEmail(email);
        return customerMapper.mapFromEntity(customerFind);
    }
}
