package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "uniqueCode")
@ToString(of = {"restaurantId", "name", "typeFood", "email", "openingHours"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "unique_code")
    private String uniqueCode;

    @Column(name = "name")
    private String name;

    @Column(name = "type_food")
    private String typeFood;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "opening_hours")
    private String openingHours;

    @Transient
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private MenuEntity menu;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_street_delivery",
            joinColumns = @JoinColumn(name = "street_delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<StreetDeliveryEntity> streetsDelivery;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    AddressEntity address;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    Set<OpinionEntity> opinions;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    OwnerEntity owner;

    @Transient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    Set<FoodOrderEntity> orders;


}
