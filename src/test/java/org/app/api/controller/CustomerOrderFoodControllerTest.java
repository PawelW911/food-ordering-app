package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.*;
import org.app.bussiness.*;
import org.app.domain.FoodOrder;
import org.app.util.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerOrderFoodController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RunWith(PowerMockRunner.class)
@PrepareForTest(CustomerOrderFoodController.class)
class CustomerOrderFoodControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private FoodOrderService foodOrderService;
    @MockBean
    private CustomerService customerService;
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
    private CustomerOrderFoodController controller;


    @Test
    void checkReturnCustomerOrdersPage() throws Exception {
        // given
        CustomerOrderFoodController spyController = spy(controller);

        doReturn(Map.of("foodOrderDTOs", Set.of(CustomerFixtures.someCustomer1())))
                .when(spyController).prepareCustomerOrders();

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/customer_orders"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("variableDTO"))
                .andExpect(model().attributeExists("completedOrderToOpinion"))
                .andExpect(view().name("customer_order_food"));
    }

    @Test
    void checkUpdateOrder() throws Exception {
        // given
        CustomerOrderFoodController spyController = spy(controller);

        doReturn(Map.of("orderAppetizersDTOs", FoodOrderFixtures.someFoodOrder2().getAppetizers(),
                "appetizerDTOs", AppetizerFixtures.someAppetizersForPolishFood()
        ))
                .when(spyController)
                .prepareOrderDishesAndAvailableDishes();

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/update_order"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appetizerDTO"))
                .andExpect(model().attributeExists("soupDTO"))
                .andExpect(model().attributeExists("mainMealDTO"))
                .andExpect(model().attributeExists("desertDTO"))
                .andExpect(model().attributeExists("drinkDTO"))
                .andExpect(model().attributeExists("sumCost"))
                .andExpect(model().attribute("variableDTO", new VariableDTO()))
                .andExpect(view().name("update_order"));
    }

    @Test
    void checkAddAppetizerToOrder() throws Exception {
        // given
        AppetizerDTO appetizerDTO = new AppetizerDTO();
        appetizerDTO.setAppetizerId(1);
        appetizerDTO.setQuantity(1);

        when(appetizerService.findById(any()))
                .thenReturn(AppetizerFixtures.someAppetizersForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_appetizer_order")
                        .flashAttr("appetizerDTO", appetizerDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));
    }

    @Test
    void checkUpdateQuantityAppetizerInOrder() throws Exception {
        // given
        AppetizerDTO appetizerDTO = new AppetizerDTO();
        appetizerDTO.setAppetizerId(1);
        appetizerDTO.setQuantity(1);

        when(appetizerService.findById(any()))
                .thenReturn(AppetizerFixtures.someAppetizersForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_quantity_appetizer_order")
                        .flashAttr("appetizerDTO", appetizerDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));

    }

    @Test
    void checkAddSoupToOrder() throws Exception {
        // given
        SoupDTO soupDTO = new SoupDTO();
        soupDTO.setSoupId(1);
        soupDTO.setQuantity(1);

        when(soupService.findById(any()))
                .thenReturn(SoupFixtures.someSoupsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_soup_order")
                        .flashAttr("soupDTO", soupDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));
    }

    @Test
    void checkUpdateQuantitySoupInOrder() throws Exception {
        // given
        SoupDTO soupDTO = new SoupDTO();
        soupDTO.setSoupId(1);
        soupDTO.setQuantity(1);

        when(soupService.findById(any()))
                .thenReturn(SoupFixtures.someSoupsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_quantity_soup_order")
                        .flashAttr("soupDTO", soupDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));

    }

    @Test
    void checkAddMainMealToOrder() throws Exception {
        // given
        MainMealDTO mainMealDTO = new MainMealDTO();
        mainMealDTO.setMainMealId(1);
        mainMealDTO.setQuantity(1);

        when(mainMealService.findById(any()))
                .thenReturn(MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_main_meal_order")
                        .flashAttr("mainMealDTO", mainMealDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));
    }

    @Test
    void checkUpdateQuantityMainMealInOrder() throws Exception {
        // given
        MainMealDTO mainMealDTO = new MainMealDTO();
        mainMealDTO.setMainMealId(1);
        mainMealDTO.setQuantity(1);

        when(mainMealService.findById(any()))
                .thenReturn(MainMealFixtures.someMainMealsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_quantity_main_meal_order")
                        .flashAttr("mainMealDTO", mainMealDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));

    }

    @Test
    void checkAddDesertToOrder() throws Exception {
        // given
        DesertDTO desertDTO = new DesertDTO();
        desertDTO.setDesertId(1);
        desertDTO.setQuantity(1);

        when(desertService.findById(any()))
                .thenReturn(DesertFixtures.someDesertsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_desert_order")
                        .flashAttr("desertDTO", desertDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));
    }

    @Test
    void checkUpdateQuantityDesertInOrder() throws Exception {
        // given
        DesertDTO desertDTO = new DesertDTO();
        desertDTO.setDesertId(1);
        desertDTO.setQuantity(1);

        when(desertService.findById(any()))
                .thenReturn(DesertFixtures.someDesertsForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_quantity_desert_order")
                        .flashAttr("desertDTO", desertDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));

    }

    @Test
    void checkAddDrinkToOrder() throws Exception {
        // given
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setDrinkId(1);
        drinkDTO.setQuantity(1);

        when(drinkService.findById(any()))
                .thenReturn(DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_drink_order")
                        .flashAttr("drinkDTO", drinkDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));
    }

    @Test
    void checkUpdateQuantityDrinkInOrder() throws Exception {
        // given
        DrinkDTO drinkDTO = new DrinkDTO();
        drinkDTO.setDrinkId(1);
        drinkDTO.setQuantity(1);

        when(drinkService.findById(any()))
                .thenReturn(DrinkFixtures.someDrinksForPolishFood().stream().toList().get(0));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/customer/update_quantity_drink_order")
                        .flashAttr("drinkDTO", drinkDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/update_order"));

    }


}