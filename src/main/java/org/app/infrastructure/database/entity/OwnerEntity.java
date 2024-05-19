package org.app.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.app.security.UserEntity;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
@ToString(of = {"ownerId", "name", "surname", "email", "phone"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owner")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "name")
    private String name;


    @Column(name = "surname")
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Transient
    @OneToMany(fetch = FetchType.LAZY)
    private Set<RestaurantEntity> restaurants;


}
