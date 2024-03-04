package org.app.infrastructure.configuration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.infrastructure.database.repository.jpa.*;
import org.app.security.RoleEntity;
import org.app.security.RoleRepository;
import org.app.security.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

@ActiveProfiles("test")
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor
@NoArgsConstructor
public class CleanDatabaseBeforeRepositoryTestAndConfiguration {

    @Autowired
    private FoodOrderJpaRepository foodOrderJpaRepository;
    @Autowired
    private DrinkJpaRepository drinkJpaRepository;
    @Autowired
    private DesertJpaRepository desertJpaRepository;
    @Autowired
    private SoupJpaRepository soupJpaRepository;
    @Autowired
    private MainMealJpaRepository mainMealJpaRepository;
    @Autowired
    private AppetizerJpaRepository appetizerJpaRepository;
    @Autowired
    private MenuJpaRepository menuJpaRepository;
    @Autowired
    private OpinionJpaRepository opinionJpaRepository;
    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;
    @Autowired
    private StreetDeliveryJpaRepository streetDeliveryJpaRepository;
    @Autowired
    private OwnerJpaRepository ownerJpaRepository;
    @Autowired
    private CustomerJpaRepository customerJpaRepository;
    @Autowired
    private AddressJpaRepository addressJpaRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void cleanDatabase() {
        drinkJpaRepository.deleteAll();
        desertJpaRepository.deleteAll();
        soupJpaRepository.deleteAll();
        mainMealJpaRepository.deleteAll();
        appetizerJpaRepository.deleteAll();
        foodOrderJpaRepository.deleteAll();
        menuJpaRepository.deleteAll();
        opinionJpaRepository.deleteAll();
        streetDeliveryJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        ownerJpaRepository.deleteAll();
        customerJpaRepository.deleteAll();
        addressJpaRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void saveRole() {
        roleRepository.saveAndFlush(RoleEntity.builder()
                .id(1)
                .role("OWNER")
                .build());
        roleRepository.saveAndFlush(RoleEntity.builder()
                .id(2)
                .role("CUSTOMER")
                .build());
    }
}
