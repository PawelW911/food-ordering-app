package org.app.infrastructure.configuration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.app.infrastructure.database.repository.jpa.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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

    @BeforeEach
    public void cleanDatabase() {
        foodOrderJpaRepository.deleteAll();
        drinkJpaRepository.deleteAll();
        desertJpaRepository.deleteAll();
        soupJpaRepository.deleteAll();
        mainMealJpaRepository.deleteAll();
        appetizerJpaRepository.deleteAll();
        menuJpaRepository.deleteAll();
        opinionJpaRepository.deleteAll();
        restaurantJpaRepository.deleteAll();
        streetDeliveryJpaRepository.deleteAll();
        ownerJpaRepository.deleteAll();
        customerJpaRepository.deleteAll();
        addressJpaRepository.deleteAll();
    }
}
