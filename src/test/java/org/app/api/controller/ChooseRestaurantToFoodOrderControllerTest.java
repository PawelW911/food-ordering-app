package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.app.bussiness.OpinionService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.StreetDeliveryService;
import org.app.domain.Restaurant;
import org.app.domain.StreetDelivery;
import org.app.util.RestaurantFixtures;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ChooseRestaurantToFoodOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(PowerMockRunner.class)
@PrepareForTest(ChooseRestaurantToFoodOrderController.class)
class ChooseRestaurantToFoodOrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private StreetDeliveryService streetDeliveryService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private MenuPosition menuPosition;

    @MockBean
    private OpinionService opinionService;

    @InjectMocks
    private ChooseRestaurantToFoodOrderController controller;


    @Test
    void chooseRestaurantPageReturnsPageWithAvailableRestaurants() throws Exception {
        // Given
        HashSet<Restaurant> restaurantSet = new HashSet<>();
        restaurantSet.add(RestaurantFixtures.someRestaurant1());
        Map<String, ?> availableRestaurants = Map.of("availableRestaurant", restaurantSet);
        // Mock street delivery
        StreetDelivery streetDelivery = new StreetDelivery();
        doReturn(streetDelivery).when(streetDeliveryService).findByStreetAndCity("Test Street", "Test City");
        // Mock available restaurants

        ChooseRestaurantToFoodOrderController spyController = spy(controller);

        // Call the actual method on the spy
        doReturn(availableRestaurants).when(spyController).prepareAvailableRestaurant(streetDelivery);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/choose_street_delivery"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("chooseStreetDeliveryDTO"))
                .andExpect(model().attributeExists("variableDTO"))
                .andExpect(view().name("choose_restaurant"));
    }

    @Test
    void chooseStreetDeliveryRedirectsToChooseRestaurantPage() throws Exception {
        // Given
        ChooseStreetDeliveryDTO chooseStreetDeliveryDTO = new ChooseStreetDeliveryDTO();
        chooseStreetDeliveryDTO.setChooseStreet("Test Street");
        chooseStreetDeliveryDTO.setChooseCity("Test City");
        // Mock street delivery
        StreetDelivery streetDelivery = new StreetDelivery();
        doReturn(streetDelivery).when(streetDeliveryService).findByStreetAndCity("Test Street", "Test City");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/enter_street_and_city")
                        .flashAttr("chooseStreetDeliveryDTO", chooseStreetDeliveryDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/choose_street_delivery"));
    }

    @Test
    void chooseRestaurantReturnsCreateFoodOrderPageWithMenuPositions() throws Exception {
        // Given
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setUniqueCode("TestUniqueCode");
        // Mock menu positions
        Map<String, Object> menuPositions = new HashMap<>();
        menuPositions.put("menuPositions", new ArrayList<MenuDTO>());
        doReturn(menuPositions).when(menuPosition).prepareMenuPositions("TestUniqueCode");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/choose_restaurant_to_order_food")
                        .flashAttr("variableDTO", variableDTO))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appetizerDTO"))
                .andExpect(model().attributeExists("soupDTO"))
                .andExpect(model().attributeExists("mainMealDTO"))
                .andExpect(model().attributeExists("desertDTO"))
                .andExpect(model().attributeExists("drinkDTO"))
                .andExpect(view().name("create_food_order"));
    }

    @Test
    void showOpinionRestaurantReturnsShowOpinionRestaurantPageWithOpinions() throws Exception {
        // Given
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setUniqueCode("TestUniqueCode");
        // Mock opinions
        Map<String, Object> opinions = new HashMap<>();
        opinions.put("opinionRestaurant", new ArrayList<OpinionDTO>());
        ChooseRestaurantToFoodOrderController spyController = spy(controller);
        doReturn(opinions).when(spyController).prepareOpinionsRestaurant("TestUniqueCode");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/show_opinion")
                        .flashAttr("variableDTO", variableDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("show_opinion_restaurant"));
    }
}