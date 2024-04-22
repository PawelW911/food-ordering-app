package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.controller.dataForController.MenuPosition;
import org.app.api.dto.*;
import org.app.api.dto.mapper.*;
import org.app.bussiness.*;
import org.app.domain.FoodOrder;
import org.app.util.AppetizerFixtures;
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

import java.math.BigDecimal;
import java.util.Map;

import static org.app.util.AppetizerFixtures.someAppetizersForPolishFood;
import static org.app.util.DesertFixtures.someDesertsForPolishFood;
import static org.app.util.DrinkFixtures.someDrinksForPolishFood;
import static org.app.util.MainMealFixtures.someMainMealsForPolishFood;
import static org.app.util.SoupFixtures.someSoupsForPolishFood;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FoodOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(PowerMockRunner.class)
@PrepareForTest(FoodOrderController.class)
class FoodOrderControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FoodOrderService foodOrderService;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AppetizerMapperDTO appetizerMapperDTO;
    @MockBean
    private SoupMapperDTO soupMapperDTO;
    @MockBean
    private MainMealMapperDTO mainMealMapperDTO;
    @MockBean
    private DesertMapperDTO desertMapperDTO;
    @MockBean
    private DrinkMapperDTO drinkMapperDTO;
    @MockBean
    private MenuPosition menuPosition;
    @MockBean
    private AppetizerService appetizerService;
    @MockBean
    private SoupService soupService;
    @MockBean
    private MainMealService mainMealService;
    @MockBean
    private DesertService desertService;
    @MockBean
    private DrinkService drinkService;

    @InjectMocks
    private FoodOrderController controller;

    @Test
    void checkCreateFoodOrderPage() throws Exception {
        // given
        FoodOrderController spyController = spy(controller);

        doReturn(Map.of(
                "appetizerDTOsAdded", someAppetizersForPolishFood().stream().toList().get(0),
                "soupDTOsAdded", someSoupsForPolishFood().stream().toList().get(0),
                "mainMealDTOsAdded", someMainMealsForPolishFood().stream().toList().get(0),
                "desertDTOsAdded", someDesertsForPolishFood().stream().toList().get(0),
                "drinkDTOsAdded", someDrinksForPolishFood().stream().toList().get(0),
                "appetizerDTOs", someAppetizersForPolishFood(),
                "soupDTOs", someSoupsForPolishFood(),
                "mainMealDTOs", someMainMealsForPolishFood(),
                "desertDTOs", someDesertsForPolishFood(),
                "drinkDTOs", someDrinksForPolishFood()
        )).when(spyController).prepareAddedDishesAndSumCost();

        when(foodOrderService.calculateCost(any(FoodOrder.class))).thenReturn(BigDecimal.valueOf(100L));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/create_food_order"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appetizerDTO"))
                .andExpect(model().attributeExists("soupDTO"))
                .andExpect(model().attributeExists("mainMealDTO"))
                .andExpect(model().attributeExists("desertDTO"))
                .andExpect(model().attributeExists("drinkDTO"))
                .andExpect(model().attributeExists("sumCost"))
                .andExpect(view().name("create_food_order"));
    }

    @Test
    void checkSubmitOrder() throws Exception {
        // given, when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/submit_order"))
                .andExpect(status().isOk())
                .andExpect(view().name("submit_order_success"));
    }

    @Test
    void checkAddAppetizer() throws Exception {
        // given
        AppetizerDTO appetizerDTO = new AppetizerDTO();
        appetizerDTO.setAppetizerId(1);
        appetizerDTO.setQuantity(1);

        when(appetizerService.findById(any()))
                .thenReturn(someAppetizersForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add_appetizer_to_order")
                .flashAttr("appetizerDTO", appetizerDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/create_food_order"));
    }

    @Test
    void checkAddSoup() throws Exception {
        // given
        SoupDTO soupDTO = new SoupDTO();
        soupDTO.setSoupId(1);
        soupDTO.setQuantity(1);

        when(soupService.findById(any()))
                .thenReturn(someSoupsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add_soup_to_order")
                .flashAttr("soupDTO", soupDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/create_food_order"));
    }

    @Test
    void checkAddMainMeal() throws Exception {
        // given
        MainMealDTO mainMealDTO = new MainMealDTO();
        mainMealDTO.setMainMealId(1);
        mainMealDTO.setQuantity(1);

        when(mainMealService.findById(any()))
                .thenReturn(someMainMealsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add_main_meal_to_order")
                .flashAttr("mainMealDTO", mainMealDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/create_food_order"));
    }

    @Test
    void checkAddDesert() throws Exception {
        // given
        DesertDTO desertDTO = new DesertDTO();
        desertDTO.setDesertId(1);
        desertDTO.setQuantity(1);

        when(desertService.findById(any()))
                .thenReturn(someDesertsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add_desert_to_order")
                .flashAttr("desertDTO", desertDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/create_food_order"));
    }

    @Test
    void checkAddDrink() throws Exception {
        // given
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setDrinkId(1);
        drinkDTO.setQuantity(1);

        when(drinkService.findById(any()))
                .thenReturn(someDrinksForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/customer/add_drink_to_order")
                .flashAttr("drinkDTO", drinkDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/create_food_order"));
    }
}