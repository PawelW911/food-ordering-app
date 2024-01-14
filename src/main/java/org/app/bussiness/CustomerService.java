package org.app.bussiness;

import lombok.AllArgsConstructor;
import org.app.business.dao.CustomerDAO;
import org.app.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public Customer saveNewCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }
}
