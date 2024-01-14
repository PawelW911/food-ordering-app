package org.app.infrastructure.database.repository.mapper;

import org.app.domain.Customer;
import org.app.infrastructure.database.entity.AddressEntity;
import org.app.infrastructure.database.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerEntity mapToEntity(Customer customer);

    default CustomerEntity mapToEntity(Customer customer, AddressEntity addressEntity) {
        return CustomerEntity.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(addressEntity)
                .build();
    }

    Customer mapFromEntity(CustomerEntity customerEntity);
}
