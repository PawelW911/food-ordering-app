package org.app.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode
@ToString(of = {"customerId", "name", "surname", "email", "phone", "address", "opinion", "order"})
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String email;
    String phone;
    Address address;
    Opinion opinion;
    FoodOrder order;

}
