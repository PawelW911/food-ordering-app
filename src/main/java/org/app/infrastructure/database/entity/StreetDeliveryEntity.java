package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "streetDeliveryId")
@ToString(of = {"streetDeliveryId", "street", "city", "restaurants"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "street_delivery")
public class StreetDeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_delivery_id")
    private Integer streetDeliveryId;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

//
//    @ManyToMany(targetEntity = RestaurantEntity.class, mappedBy = "streetsDelivery", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
//    private Set<RestaurantEntity> restaurant;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "restaurant_street_delivery",
            joinColumns = @JoinColumn(name = "street_delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<RestaurantEntity> restaurants;

}
