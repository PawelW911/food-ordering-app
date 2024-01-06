package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
@ToString(of = {"customerId", "name", "surname", "email", "phone"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<OpinionEntity> opinions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<FoodOrderEntity> orders;


}
