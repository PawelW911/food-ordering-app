package org.app.api.controller;

import lombok.AllArgsConstructor;
import org.app.api.dto.MainMealDTO;
import org.app.api.dto.mapper.MainMealMapperDTO;
import org.app.bussiness.MainMealService;
import org.app.bussiness.MenuService;
import org.app.bussiness.RestaurantService;
import org.app.domain.MainMeal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.app.util.MainMealFixtures.someMainMealsForPolishFood;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = MainMealController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MainMealControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MainMealService mainMealService;
    @MockBean
    private MainMealMapperDTO mainMealMapperDTO;
    @MockBean
    private MenuService menuService;
    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testAddMainMeal() throws Exception {
        // Given
        MainMealDTO mainMealDTO = new MainMealDTO();

        when(mainMealService.saveNewMainMeal(any(MainMeal.class)))
                .thenReturn(someMainMealsForPolishFood().stream().toList().get(0));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post("/owner/add_main_meal")
                        .flashAttr("mainMealDTO", mainMealDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }

    @Test
    public void testDeleteMainMeal() throws Exception {
        // Given
        MainMealDTO mainMealDTO = new MainMealDTO();

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/owner/delete_main_meal")
                        .flashAttr("mainMealDTO", mainMealDTO))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/owner/restaurant/manage/menu"));
    }
}