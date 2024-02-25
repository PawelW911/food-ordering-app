package org.app.util;

import org.app.domain.Customer;

public class CustomerFixtures {
    public static Customer someCustomer1() {
        return Customer.builder()
                .name("Stefan")
                .surname("Kowalski")
                .email("stefan@gmail.com")
                .phone("+48 542 482 841")
                .user(UserFixtures.someUserForCustomer())
                .address(AddressFixtures.someAddress1())
                .build();
    }
}
