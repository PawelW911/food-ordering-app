package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "streetDeliveryId")
@ToString(of = {"streetDeliveryId", "street", "city"})
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_street_delivery",
            joinColumns = @JoinColumn(name = "street_delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "restaurant_id")
    )
    private Set<RestaurantEntity> restaurant;


}
