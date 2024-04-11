package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.app.bussiness.OpinionService;
import org.app.bussiness.RestaurantService;
import org.app.bussiness.StreetDeliveryService;
import org.app.domain.StreetDelivery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ChooseRestaurantToFoodOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
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
    void chooseRestaurantPage_ReturnsPageWithAvailableRestaurants() throws Exception {
        // Given
        Map<String, Object> availableRestaurants = new HashMap<>();
        // Mock street delivery
        StreetDelivery streetDelivery = new StreetDelivery();
        doReturn(streetDelivery).when(streetDeliveryService).findByStreetAndCity("Test Street", "Test City");
        // Mock available restaurants
        availableRestaurants.put("availableRestaurantDTOs", new ArrayList<RestaurantDTO>());

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/choose_street_delivery"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("chooseStreetDeliveryDTO"))
                .andExpect(model().attributeExists("variableDTO"))
                .andExpect(view().name("choose_restaurant"))
                .andExpect(model().attribute("availableAvailableRestaurant", availableRestaurants));
    }

    @Test
    void chooseStreetDelivery_RedirectsToChooseRestaurantPage() throws Exception {
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
    void chooseRestaurant_ReturnsCreateFoodOrderPageWithMenuPositions() throws Exception {
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
                .andExpect(view().name("create_food_order"))
                .andExpect(model().attribute("menuPositions", menuPositions));
    }

    @Test
    void showOpinionRestaurant_ReturnsShowOpinionRestaurantPageWithOpinions() throws Exception {
        // Given
        VariableDTO variableDTO = new VariableDTO();
        variableDTO.setUniqueCode("TestUniqueCode");
        // Mock opinions
        Map<String, Object> opinions = new HashMap<>();
        opinions.put("opinionRestaurant", new ArrayList<OpinionDTO>());
        Method method = ChooseRestaurantToFoodOrderController.class.getDeclaredMethod("prepareOpinionsRestaurant", String.class);
        method.setAccessible(true);
        Map<String, ?> preparedOpinions = (Map<String, ?>) method.invoke(controller, "TestUniqueCode");
        doReturn(opinions).when(controller).prepareOpinionsRestaurant("TestUniqueCode");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/show_opinion")
                        .flashAttr("variableDTO", variableDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("show_opinion_restaurant"))
                .andExpect(model().attribute("opinions", opinions));
    }
}