package org.app.bussiness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.bussiness.dao.CustomerDAO;
import org.app.domain.Customer;
import org.app.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;

    @Transactional
    public Customer saveNewCustomer(Customer customer) {
        Customer savedCustomer = customerDAO.saveCustomer(customer);
        if (savedCustomer == null) {
            log.error("The attempt to save the customer wasn't a success");
        } else {
            log.info("Save customer a success, id: [{}]", savedCustomer.getCustomerId());
        }
        return savedCustomer;
    }

    public Customer findCustomerByEmail(String email) {
        Customer customer = customerDAO.findByEmail(email);
        if (customer == null) {
            throw new NotFoundException("Customer with email: [%s] does not exist.".formatted(email));
        } else {
            log.info("Customer with email: [{}] is found", email);
        }
        return customer;
    }
}
