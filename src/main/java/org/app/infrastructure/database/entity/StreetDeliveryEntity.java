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


    @ManyToMany(mappedBy = "streetsDelivery")
    private Set<RestaurantEntity> restaurant;


}
