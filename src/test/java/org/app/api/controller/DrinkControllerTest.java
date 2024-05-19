package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.DrinkDTO;
import org.app.api.dto.mapper.DrinkMapperDTO;
import org.app.bussiness.DrinkService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.Drink;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.app.util.DrinkFixtures.someDrinksForPolishFood;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = DrinkController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DrinkControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private DrinkService drinkService;
    @MockBean
    private DrinkMapperDTO drinkMapperDTO;
    @MockBean
    private MenuService menuService;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testAddDrink() throws Exception {
        // Given
        DrinkDTO drinkDTO = new DrinkDTO();

        when(drinkService.saveNewDrink(any(Drink.class)))
                .thenReturn(someDrinksForPolishFood().stream().toList().get(0));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_drink")
                        .flashAttr("drinkDTO", drinkDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }

    @Test
    public void testDeleteDrink() throws Exception {
        // Given
        DrinkDTO drinkDTO = new DrinkDTO();

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/owner/delete_drink")
                        .flashAttr("drinkDTO", drinkDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }
}