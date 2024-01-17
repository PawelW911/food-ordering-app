package org.app.bussiness;

import org.app.bussiness.dao.CustomerDAO;
import org.app.domain.Customer;
import org.app.util.CustomerFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void checkSaveNewCustomer() {
        // given
        Customer customerExample = CustomerFixtures.someCustomer1();

        Mockito.when(customerDAO.saveCustomer(Mockito.any(Customer.class))).thenReturn(customerExample);

        // when
        Customer customer = customerService.saveNewCustomer(customerExample);

        // then
        Assertions.assertNotNull(customer);
    }
}
