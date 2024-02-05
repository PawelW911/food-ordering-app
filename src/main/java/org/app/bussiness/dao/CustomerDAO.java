package org.app.bussiness.dao;

import org.app.domain.Customer;

public interface CustomerDAO {
    Customer saveCustomer(Customer customer);

    Customer findByEmail(String email);
}
